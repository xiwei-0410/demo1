package com.wxw.ceshimanchine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxw.util.Base64Util;
import com.wxw.util.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 该类表示火种设备在线版（先把数据对接到火种平台）
 * @author wxw
 */
public class OnlineVersion {

    private static final Logger logger = LoggerFactory.getLogger(OnlineVersion.class);
    /**
     * 火种平台地址
     */
    private static final String URL = "https://web.qy-rgs.com/api";
    /**
     * appid
     */
    private static final String APP_ID = "375acf1db8c34914a9182798c764f1ff";
    /**
     * 密钥
     */
    private static final String APP_SECRET = "F1F11A01905E45D3997478431270F135";

    /**
     * 加密
     * @param faceType 请求链接
     * @param appID appid
     * @param appSecret 密钥
     * @return
     */
    public static Map<String,Object> encryption(String faceType,String appID,String appSecret){
        Map<String, Object> faceMap = new HashMap<>(16);
        String faceRequestId = UUID.randomUUID().toString().replace("-", "");
        String faceTimestamp = new SimpleDateFormat("yyyyMMddHHmms").format(new Date());
        faceMap.put("type", faceType);
        faceMap.put("appId", appID);
        faceMap.put("requestId", faceRequestId);
        faceMap.put("timestamp", faceTimestamp);
        faceMap.put("sign", MD5.getMd5Value(appID + faceRequestId + faceType + appSecret + faceTimestamp).toLowerCase());
        return faceMap;
    }

    /**
     *  添加人员数据到火种平台
     * @param map
     * @return
     */
    public static String addUser(Map<String,Object> map){
        try {
            Map<String,Object> faceMap = encryption("PERSON_CREATE_OR_UPDATE",APP_ID,APP_SECRET);
            Map<String, Object> faceBody = new HashMap<>(16);
            faceBody.put("type", 1);
            faceBody.put("code","123456789987456321");
            faceBody.put("name","测试");
            List<String> photo  = new ArrayList<>();
            photo.add(Base64Util.getImgStr("C:\\Users\\wxw\\Desktop/photo.jpg"));
            faceBody.put("faces",photo);
            faceMap.put("body", faceBody);
            //请求接口
            JSONObject jsonObject = JSON.parseObject(MD5.send(URL, JSON.toJSONString(faceMap)));
            System.out.println(jsonObject);
            return jsonObject.getJSONObject("body").getString("personId");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 查询设备列表数据
     * @param
     * @return
     */
    public static Object selectManchine(){
        try {
            Map<String,Object> faceMap = encryption("DEVICE_LIST",APP_ID,APP_SECRET);
            //请求接口
            JSONObject jsonObject = JSON.parseObject(MD5.send(URL, JSON.toJSONString(faceMap)));
            System.out.println(jsonObject);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 人员授权设备
     * personId 人员 ID
     * deviceIds 设备id
     * @return
     */
    public static Object empowerManchine(String personId,String deviceIds){
        try {
            List<String> list = new ArrayList<>();
            Map<String,Object> faceMap = encryption("PERSON_GRANT_DEVICE",APP_ID,APP_SECRET);
            Map<String, Object> faceBody = new HashMap<>(16);
            faceBody.put("personId",personId);
            list.add(deviceIds);
            faceBody.put("deviceIds",list);
            faceBody.put("passMonday",true);
            faceBody.put("passTuesday",true);
            faceBody.put("passWednesday",true);
            faceBody.put("passThursday",true);
            faceBody.put("passFriday",true);
            faceBody.put("passSaturday",true);
            faceBody.put("passSunday",true);
            faceMap.put("body", faceBody);
            //请求接口
            JSONObject jsonObject = JSON.parseObject(MD5.send(URL, JSON.toJSONString(faceMap).toString()));
            System.out.println(jsonObject);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  "";
    }


    /**
     * 打卡记录消息回调
     * @param json
     * @return
     */
    @RequestMapping(value = "/receive", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public  JSONObject receive(@RequestBody final JSONObject json) {
        System.out.println("接收推送消息："+json.toJSONString());
        //必须要有返回值，这样才会推送下一条，会一直推送同一条数据
        JSONObject rtn = new JSONObject();
        rtn.put("type",json.getString("type"));
        rtn.put("code","SUCCESS");
        rtn.put("requestId",json.getString("requestId"));
        return rtn;
    }

    /**
     * 获取识别记录图片
     * @param id 推送记录id
     * @return
     */
    public static Object getImgById(String id){
        try {
            Map<String,Object> faceMap = encryption("RECOGNITION_PICTURE",APP_ID,APP_SECRET);
            Map<String, Object> faceBody = new HashMap<>(16);
            faceBody.put("id",id);
            faceMap.put("body", faceBody);
            //请求接口
            JSONObject jsonObject = JSON.parseObject(MD5.send(URL, JSON.toJSONString(faceMap).toString()));
            System.out.println(jsonObject);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  "";
    }



}

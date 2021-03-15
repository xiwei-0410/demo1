package com.wxw.ceshimanchine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxw.util.*;
import okhttp3.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/api/machine/")
@Controller
public class Manchine {
    private static final String URL = "https://web.qy-rgs.com/api";
    private static final String APP_ID = "375acf1db8c34914a9182798c764f1ff";
    private static final String APP_SECRET = "F1F11A01905E45D3997478431270F135";


    public static void main(String[] args) throws IOException {
        String url = "http://192.168.31.10:8090/setIdentifyCallBack";
        String parame ="pass=123456&callbackUrl=http://192.168.31.126:8080/api/machine/callback";
        System.out.println(HttpUtils.HttpPost(url,parame));
    }

    @RequestMapping("ceshiSend")
    @ResponseBody
    public Object ceshiSend(){
        try {
            SendDemo.send();
        } catch (IOException e) {
            System.out.println("发送信息报错："+e);
        } catch (InterruptedException e) {
            System.out.println("发送信息报错："+e);
        }
        return 0;
    }

    /**
     * 消息回调
     * @param json
     * @return
     */
//    @RequestMapping(value = "/receive", produces = {"application/json;charset=UTF-8"})
//    @ResponseBody
//    public  JSONObject receive(@RequestBody final JSONObject json) {
//        System.out.println("接收推送消息："+json.toJSONString());
//        JSONObject rtn = new JSONObject();
//        rtn.put("type",json.getString("type"));
//        rtn.put("code","SUCCESS");
//        rtn.put("requestId",json.getString("requestId"));
//        return rtn;
//    }

    /**
     * 设备识别记录回调
     * @param request
     * @return
     */
//    @RequestMapping("/record/notice")
//    @ResponseBody
//    public JSONObject a1(HttpServletRequest request){
//        String json = "";
//        try {
//            json = GetRequestJsonUtils.getRequestJsonObject2(request);
//            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "识别记录===：" + json);
//            JSONObject object = JSONObject.parseObject(json);
//            //返回状态
//            JSONObject rtn = new JSONObject();
//            rtn.put("sn",object.getString("sn"));
//            rtn.put("success",true);
//            rtn.put("data",object.getJSONArray("data"));
//            return rtn;
//        } catch (Exception e) {
//            json = "接口访问成功，没有接受到数据";
//            System.out.println("数据格式错误");
//        }
//        return null;
//    }

    /**
     * 设备识别图片回调
     * @param request
     * @return
     */
    @RequestMapping("/callback")
    @ResponseBody
    public JSONObject a2(HttpServletRequest request){
        String json = "";
        try {
            json = GetRequestJsonUtils.getRequestJsonObject2(request);
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "识别图片记录===：" + json);
            //返回状态
            JSONObject rtn = new JSONObject();
            rtn.put("success",true);
            rtn.put("result",1);
            return rtn;
        } catch (Exception e) {
            json = "接口访问成功，没有接受到数据";
            System.out.println("数据格式错误");
        }
        return null;
    }

    /**
     * 新增人员数据
     * @param
     * @return
     */
    public static String addUser(){
        try {
            String faceType = "PERSON_CREATE_OR_UPDATE";
            String faceRequestId = UUID.randomUUID().toString().replace("-", "");
            String faceTimestamp = new SimpleDateFormat("yyyyMMddHHmms").format(new Date());
            Map<String, Object> faceMap = new HashMap<>(16);
            faceMap.put("type", faceType);
            faceMap.put("appId", APP_ID);
            faceMap.put("requestId", faceRequestId);
            faceMap.put("timestamp", faceTimestamp);
            faceMap.put("sign", MD5.getMd5Value(APP_ID + faceRequestId + faceType + APP_SECRET + faceTimestamp).toLowerCase());
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
     * 查询人员列表数据
     * @param
     * @return
     */
    public static Object selectUser(){
        try {
            String faceType = "PERSON_LIST";
            String faceRequestId = UUID.randomUUID().toString().replace("-", "");
            String faceTimestamp = new SimpleDateFormat("yyyyMMddHHmms").format(new Date());
            Map<String, Object> faceMap = new HashMap<>(16);
            faceMap.put("type", faceType);
            faceMap.put("appId", APP_ID);
            faceMap.put("requestId", faceRequestId);
            faceMap.put("timestamp", faceTimestamp);
            faceMap.put("sign", MD5.getMd5Value(APP_ID + faceRequestId + faceType + APP_SECRET + faceTimestamp).toLowerCase());
            Map<String, Object> faceBody = new HashMap<>(16);
            faceBody.put("currentPage",0);
            faceBody.put("pageSize",100);
            faceMap.put("body",faceBody);
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
     * 查询设备列表数据
     * @param
     * @return
     */
    public static Object selectManchine(){
        try {
            String faceType = "DEVICE_LIST";
            String faceRequestId = UUID.randomUUID().toString().replace("-", "");
            String faceTimestamp = new SimpleDateFormat("yyyyMMddHHmms").format(new Date());
            Map<String, Object> faceMap = new HashMap<>(16);
            faceMap.put("type", faceType);
            faceMap.put("appId", APP_ID);
            faceMap.put("requestId", faceRequestId);
            faceMap.put("timestamp", faceTimestamp);
            faceMap.put("sign", MD5.getMd5Value(APP_ID + faceRequestId + faceType + APP_SECRET + faceTimestamp).toLowerCase());
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
     * @return
     */
    public static Object empowerManchine(String personId){
        try {
            String faceType = "PERSON_GRANT_DEVICE";
            String faceRequestId = UUID.randomUUID().toString().replace("-", "");
            String faceTimestamp = new SimpleDateFormat("yyyyMMddHHmms").format(new Date());
            Map<String, Object> faceMap = new HashMap<>(16);
            faceMap.put("type", faceType);
            faceMap.put("appId", APP_ID);
            faceMap.put("requestId", faceRequestId);
            faceMap.put("timestamp", faceTimestamp);
            faceMap.put("sign", MD5.getMd5Value(APP_ID + faceRequestId + faceType + APP_SECRET + faceTimestamp).toLowerCase());
            Map<String, Object> faceBody = new HashMap<>(16);
            faceBody.put("personId",personId);
            List<String> list = new ArrayList<>();
            list.add("2b98f3693f844ae18ed2af092ed4d8b7");
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
     * 人员设备授权通知
     * @return
     */
    public static Object notifyGrant(){
        try {
            String faceType = "NOTIFY_GRANT";
            String faceRequestId = UUID.randomUUID().toString().replace("-", "");
            String faceTimestamp = new SimpleDateFormat("yyyyMMddHHmms").format(new Date());
            Map<String, Object> faceMap = new HashMap<>(16);
            faceMap.put("type", faceType);
            faceMap.put("appId", APP_ID);
            faceMap.put("requestId", faceRequestId);
            faceMap.put("timestamp", faceTimestamp);
            faceMap.put("sign", MD5.getMd5Value(APP_ID + faceRequestId + faceType + APP_SECRET + faceTimestamp).toLowerCase());
            Map<String, Object> faceBody = new HashMap<>(16);
            faceBody.put("time","2020-12-10 10:58:22");
            faceBody.put("personId","689c60e66c794c49a85a5834194fe629");
            faceBody.put("deviceId","2b98f3693f844ae18ed2af092ed4d8b7");
            faceBody.put("success",true);
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
     * 获取识别记录图片
     * @param id 设备记录id
     * @return
     */
    public static Object getImgById(String id){
        try {
            String faceType = "RECOGNITION_PICTURE";
            String faceRequestId = UUID.randomUUID().toString().replace("-", "");
            String faceTimestamp = new SimpleDateFormat("yyyyMMddHHmms").format(new Date());
            Map<String, Object> faceMap = new HashMap<>(16);
            faceMap.put("type", faceType);
            faceMap.put("appId", APP_ID);
            faceMap.put("requestId", faceRequestId);
            faceMap.put("timestamp", faceTimestamp);
            faceMap.put("sign", MD5.getMd5Value(APP_ID + faceRequestId + faceType + APP_SECRET + faceTimestamp).toLowerCase());
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

package com.mkl.ceshimanchine;

import com.mkl.util.HttpUtils;

/**
 * 该类表示火种设备离线版（局域网）
 * 步骤   1、设备进行接口密码设置
 *        2、设备进行回调地址设置（回调地址为接收数据接口）
 * @author mkl
 */
public class OfflineVersion {

    /**
     * 接口密码请求地址（设备ip:8090）
     */
    private static final String PASS_URL = "http://192.168.31.10:8090/setPassWord";

    /**
     * 设置识别回调地址
     */
    private static final String CALL_BACK_URL="http://192.168.31.10:8090/setIdentifyCallBack";

    /**
     * 人员删除
     */
    private static final String DELETE_USER = "http://192.168.31.10:8090/person/delete";

    /**
     * 设置接口密码 (传入两个密码需一样)
     * @param oldPass
     * @param newPass
     * @return
     */
    public Object setPass(String oldPass,String newPass){
        String parame = "oldPass="+oldPass+"&newPass="+newPass;
        return HttpUtils.HttpPost(PASS_URL,parame);
    }

    /**
     * 设置设备回调地址
     * @param pass 接口安全校验秘钥（设置的接口密码）
     * @param callbackUrl 回调地址
     * @return
     */
    public Object setCallBack(String pass,String callbackUrl){
        String parame = "pass="+pass+"&callbackUrl="+callbackUrl;
        return HttpUtils.HttpPost(CALL_BACK_URL,parame);
    }

    /**
     * 删除人员信息
     * @param pass 接口安全校验秘钥（设置的接口密码）
     * @param id  （1）删除多个人员，personId 用英文逗号拼接
     *            （2）传入-1 则删除所有人员
     * @return
     */
    public static Object delUserAll(String pass, String id){
        String parame = "pass="+pass+"&id="+id;
        return HttpUtils.HttpPost(DELETE_USER,parame);
    }

}

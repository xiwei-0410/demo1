package com.mkl.util;

import com.alibaba.fastjson.JSONObject;
import com.mkl.suaggerDemo.service.SmsTemplate;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class TokenUtil {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String oauthModel = "CC";
//		String accessToken = getAccessToken(oauthModel);
//		System.err.println(accessToken);
        try {
            System.out.println(sendSms("18091389628",""));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
	
	public static String getAccessToken(String oauth_type) {
		String req_url = SmsConstants.ACCESS_TOKEN_URL;
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("app_id", SmsConstants.APP_ID));
		formparams.add(new BasicNameValuePair("app_secret", SmsConstants.APP_SECRET));
		formparams.add(new BasicNameValuePair("redirect_uri", SmsConstants.REDIRECT_URI));
		if("AC_1".equals(oauth_type)) {
			return SmsConstants.GET_AC_URL;
		} else if("AC_2".equals(oauth_type)) {
			formparams.add(new BasicNameValuePair("grant_type", "authorization_code"));
			formparams.add(new BasicNameValuePair("code", SmsConstants.AUTHORIZATION_CODE));
		} else if("IG".equals(oauth_type)) {
			req_url = SmsConstants.AUTHORIZE_URL;
			formparams.add(new BasicNameValuePair("response_type", "token"));
		} else if("CC".equals(oauth_type)) {
			formparams.add(new BasicNameValuePair("grant_type", "client_credentials"));
		} else if("refresh".equals(oauth_type)) {
			formparams.add(new BasicNameValuePair("grant_type", "refresh_token"));
			formparams.add(new BasicNameValuePair("refresh_token", SmsConstants.REFRESH_TOKEN));
		}
		return HttpsUtil.doPost(req_url, formparams, true);
	}

    public static Map<String, String> sendSms(String tel, String... param) throws Exception {
        //访问令牌AT-------CC模式，AC模式都可，推荐CC模式获取令牌
        String ACCESS_TOKEN = JSONObject.parseObject((getAccessToken("CC"))).getString("access_token");
        //获取当前时间
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        Map<String, String> map = null;
        String str = RandomUtil.randomFor6();
        if (param.length > 0) {
            map = SmsTemplate.templat(param);
        }
        //这里存放模板参数，如果模板没有参数直接用template_param={}
        String template_param = "{}";
        System.out.println(template_param + "=====template_param");
        String postEntity = "app_id=" + SmsConstants.APP_ID + "&access_token="
                + ACCESS_TOKEN + "&acceptor_tel=" + tel + "&template_id="
                + SmsConstants.TEMPLATE_ID + "&template_param=" + template_param
                + "&timestamp=" + URLEncoder.encode(timestamp, "utf-8");
        System.out.println(postEntity);
        String resJson = "";
        Map<String, String> map2 = new HashMap<String, String>();

        resJson = HttpInvoker.httpPost1(SmsConstants.POSTURL, null, postEntity);
        System.err.println(resJson);
        map2.put("vcode", str);
        return map2;
    }



}

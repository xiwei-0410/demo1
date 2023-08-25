package com.mkl.util;


public class SmsConstants {

    //应用ID------登录平台在应用设置可以找到

    public static final String APP_ID = "480689690000277488";

    /**
     * 短信接口相关参数
     */

    //应用secret-----登录平台在应用设置可以找到
    public static final String APP_SECRET = "fe7dcabb5fcc4178b20d9528d28a17b8";
    //模板ID
    public static String TEMPLATE_ID = "91557014";
    public static final String AUTHORIZATION_CODE = "";
    public static final String REFRESH_TOKEN = "";

    public static final String POSTURL = "http://api.189.cn/v2/emp/templateSms/sendSms";
    public static final String REDIRECT_URI = "https://oauth.api.189.cn/emp/";
    public static final String AUTHORIZE_URL = "https://oauth.api.189.cn/emp/oauth2/v3/authorize";
    public static final String ACCESS_TOKEN_URL = "https://oauth.api.189.cn/emp/oauth2/v3/access_token";
    public static final String LOGOUT_URL = "https://oauth.api.189.cn/emp/oauth2/v3/logout";
    public static final String GET_AC_URL = "https://oauth.api.189.cn/emp/oauth2/v3/authorize?app_id=" + APP_ID
                    + "&redirect_uri=" + REDIRECT_URI + "&response_type=code";




}

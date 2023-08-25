package com.mkl.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5 {
    public static String getMd5Value(String value){
        try {
            //1. 获得md5加密算法工具类
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //2. 加密的结果为十进制
            byte[] md5Bytes = messageDigest.digest(value.getBytes());
            //3. 将md5加密算法值转化为16进制
            BigInteger bigInteger = new BigInteger(1, md5Bytes);
            return bigInteger.toString(16);

        } catch (Exception e) {
            //如果产生错误则抛出异常
            throw new RuntimeException();
        }
    }

    /**
     * 发送post请求
     * @param url  路径
     * @param jsonObject  参数(json类型)
     * @return
     */
    public static String send(String url, String jsonObject) throws Exception{
        String body = "";
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        StringEntity s = new StringEntity(jsonObject, "utf-8");
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                "application/json"));
        //设置参数到请求对象中
        httpPost.setEntity(s);
        //设置header信息
        httpPost.setHeader("Content-type", "application/json");

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, "UTF-8");
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }

    public static void main(String[] args) {
        System.out.println(getMd5Value("nonceStr=CDpZMgSK&secretKey=AA9C6EC0BEFE40C7BE51AA198F79873A&timestamp=1590128385351"));
        //System.out.println(System.currentTimeMillis());
    }

}

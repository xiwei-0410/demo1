package com.mkl.util;

import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mkl
 */
@SuppressWarnings("deprecation")
public class HttpUtils {

    /**
     * 发送get请求，参数用map接收
     * @param url 地址
     * @param map 参数
     * @return 返回值
     */
	public static String getMap(String url, Map<String, String> map)
	{
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for(Map.Entry<String,String> entry : map.entrySet())
		{
			pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
		}
		CloseableHttpResponse response = null;
		try {
			URIBuilder builder = new URIBuilder(url);
			builder.setParameters(pairs);
			HttpGet get = new HttpGet(builder.build());
			response = httpClient.execute(get);
			if(response != null && response.getStatusLine().getStatusCode() == 200)
			{
				HttpEntity entity = response.getEntity();
				result = entityToString(entity);
			}
			return result;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				httpClient.close();
				if(response != null)
				{
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 发送post请求，参数用map接收
	 * @param url 地址
	 * @param map 参数
	 * @return 返回值
	 */
	public static String postMap(String url,Map<String,String> map) {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for(Map.Entry<String,String> entry : map.entrySet())
		{
			pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
		}
		CloseableHttpResponse response = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));
			response = httpClient.execute(post);
			if(response != null && response.getStatusLine().getStatusCode() == 200)
			{
				HttpEntity entity = response.getEntity();
				result = entityToString(entity);
			}
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				httpClient.close();
				if(response != null)
				{
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	private static String entityToString(HttpEntity entity) throws IOException {
		String result = null;
		if(entity != null)
		{
			long lenth = entity.getContentLength();
			if(lenth != -1 && lenth < 2048)
			{
				result = EntityUtils.toString(entity,"UTF-8");
			}else {
				InputStreamReader reader1 = new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8);
				CharArrayBuffer buffer = new CharArrayBuffer(2048);
				char[] tmp = new char[1024];
				int l;
				while((l = reader1.read(tmp)) != -1) {
					buffer.append(tmp, 0, l);
				}
				result = buffer.toString();
			}
		}
		return result;
	}

	/**
	 * 发送post请求
	 * @param url 地址链接
	 * @param parame 参数
	 * @return
	 */
	public static String HttpPost(String url,String parame){
		String jsonObject = null;
		try {
			OkHttpClient client = new OkHttpClient().newBuilder()
					.build();
			MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
			RequestBody body = RequestBody.create(mediaType, parame);
			Request request = new Request.Builder()
					.url(url)
					.method("POST", body)
					.addHeader("Content-Type", "application/x-www-form-urlencoded")
					.build();
			Response response = null;
			response = client.newCall(request).execute();
			System.out.println(response.body().string());
			jsonObject = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}
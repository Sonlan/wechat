package com.song.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

public class WxMenuUtils {
	// http�ͻ���
	 public static DefaultHttpClient httpclient;
	 
	 static {
	  httpclient = new DefaultHttpClient();
	 }

/*	 public static void main(String[] args) {
	  try {
	   // �����˵�
	   //String s = "{\"button\":[{\"name\":\"��������\",\"sub_button\":[{\"type\":\"click\",\"name\":\"Ц����ȫ\",\"key\":\"m_joke\"},{\"type\":\"click\",\"name\":\"�ں�����\",\"key\":\"m_duanzi\"},{\"type\":\"click\",\"name\":\"��ЦͼƬ\",\"key\":\"m_laughImg\"}]},{\"name\":\"ʵ�ù���\",\"sub_button\":[{\"type\":\"click\",\"name\":\"������ѯ\",\"key\":\"m_weather\"},{\"type\":\"click\",\"name\":\"������ѯ\",\"key\":\"m_bus\"}]},{\"type\":\"click\",\"name\":\"��������\",\"key\":\"m_about\"}]}";
	   String s = "{\"button\":[{\"name\":\"��������\",\"sub_button\":[{\"type\":\"click\",\"name\":\"Ц����ȫ\",\"key\":\"m_joke\"},{\"type\":\"click\",\"name\":\"�ں�����\",\"key\":\"m_duanzi\"},{\"type\":\"click\",\"name\":\"��ЦͼƬ\",\"key\":\"m_laughImg\"}]},{\"name\":\"ʵ�ù���\",\"sub_button\":[{\"type\":\"click\",\"name\":\"������ѯ\",\"key\":\"m_weather\"},{\"type\":\"click\",\"name\":\"������ѯ\",\"key\":\"m_bus\"},{\"type\":\"click\",\"name\":\"���ܲ˵�\",\"key\":\"m_sysmenu\"}]},{\"name\":\"��Ϣʾ��\",\"sub_button\":[{\"type\":\"click\",\"name\":\"��������\",\"key\":\"m_about\"},{\"type\":\"click\",\"name\":\"ͼ����Ϣ\",\"key\":\"m_imgmsg\"},{\"type\":\"click\",\"name\":\"������Ϣ\",\"key\":\"m_musicmsg\"}]}]}";
	   String res = createMenu(s, token);
	   System.out.println(res);
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	 }*/
	 private static String getAccess_token(){  

		  String APPID="wxa76ca32f306d7277";
		  String APPSECRET="d4624c36b6795d1d99dcf0547af5443d";

		       String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ APPID + "&secret=" +APPSECRET;
		       String accessToken = null;
		      try {
		             URL urlGet = new URL(url);
		             HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();    

		             http.setRequestMethod("GET");      //������get��ʽ����    
		             http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
		             http.setDoOutput(true);        
		             http.setDoInput(true);
		             System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//���ӳ�ʱ30��
		             System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //��ȡ��ʱ30��

		             http.connect();

		             InputStream is =http.getInputStream();
		             int size =is.available();
		             byte[] jsonBytes =new byte[size];
		             is.read(jsonBytes);
		             String message=new String(jsonBytes,"UTF-8");
		             JSONObject demoJson = JSONObject.fromObject(message);
		             accessToken = demoJson.getString("access_token");

		             System.out.println(message);
		             } catch (Exception e) {
		                 e.printStackTrace();
		             }
		        return accessToken;
	}
	 
	 /**
	  * �����˵�
	  */
	 public static String createMenu(String params, String accessToken) throws Exception {
	  HttpPost httpost = new HttpPost("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + getAccess_token());
	  httpost.setEntity(new StringEntity(params, "UTF-8"));
	  HttpResponse response = httpclient.execute(httpost);
	  String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
	  System.out.println(jsonStr);
	  return jsonStr;
	 }
	 
	 /**
	  * ��ѯ�˵�
	  *//*
	 public static String getMenuInfo(String accessToken) throws Exception {
	  HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + accessToken);
	  HttpResponse response = httpclient.execute(get);
	  String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
	  return jsonStr;
	 }
	 
	 *//**
	  * ɾ���Զ���˵�
	  *//*
	 public static String getAccessToken(String accessToken) throws Exception {
	  HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + accessToken);
	  HttpResponse response = httpclient.execute(get);
	  String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
	  JSONObject object = JSON.parseObject(jsonStr);
	  return object.getString("errmsg");
	 }
	}*/
}

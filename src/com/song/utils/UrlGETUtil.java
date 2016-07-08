package com.song.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;


public class UrlGETUtil {
	public static JSONObject GET(String url){
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
	        return JSONObject.fromObject(message);
		} catch (Exception e) {
			return null;
		}
		
	}
}

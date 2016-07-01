package com.song.utils;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
/**
 * json相关工具
 * @author Songsong
 *
 */
public class JsonUtil {

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    public static String toJson (Object data){
        return gson.toJson(data);
    }

    public static<T> T toObject (String json, Class<T> clazz) throws JsonSyntaxException{
        return gson.fromJson(json,clazz);
    }
    
    /**
     * 将输入参数整合为json格式
     * @param errorCode
     * @param errorMsg
     * @param param
     * @return json字符�?
     */
	public  static <T> String statusResponse (int errorCode,Object errorMsg, T param){
			
			HashMap<String, Object> statusResponse = new HashMap<String, Object>();
			statusResponse.put("errorCode", errorCode);
			statusResponse.put("errorMsg", errorMsg);
			statusResponse.put("param", param);
			System.out.println(gson.toJson(statusResponse));
			return gson.toJson(statusResponse);
		}
}
package com.song.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.song.utils.UrlGETUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/test")
public class test {
	private static final String APPID = "wxa76ca32f306d7277";
	private static final String APPSECRET = "d4624c36b6795d1d99dcf0547af5443d";
	
	@RequestMapping(value="/index")
	public void  index(HttpServletResponse response) throws IOException{
		String REDIRECT_URI = URLEncoder.encode("https://1u5186s163.51mypc.cn/wechat/test/toIndex", "utf-8");
		String SCOPE = "snsapi_userinfo";  //snsapi_userinfo,snsapi_base
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="+REDIRECT_URI+"&response_type=code&scope="+SCOPE+"&state=123#wechat_redirect";
		response.sendRedirect(url);
	}
	
	@RequestMapping(value="/toIndex")
	public String  toIndex(@RequestParam String code){
		//获取token
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APPID+"&secret="+APPSECRET+"&code="+code+"&grant_type=authorization_code";
		String ACCESS_TOKEN = "";
		String OPENID = "";
		try {
			JSONObject demoJson = UrlGETUtil.GET(url);
	        ACCESS_TOKEN = demoJson.getString("access_token");
	        OPENID = demoJson.getString("openid");
	        System.out.println(ACCESS_TOKEN+"  "+OPENID);
	        
	        //获取用户信息
	        String url1 = "https://api.weixin.qq.com/sns/userinfo?access_token="+ACCESS_TOKEN+"&openid="+OPENID+"&lang=zh_CN";
	        JSONObject demoJson1 = UrlGETUtil.GET(url1);
	        String nikename = demoJson1.getString("nickname");
	        System.out.println(nikename);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
		return "test/index";
	}
}

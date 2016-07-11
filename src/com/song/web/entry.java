package com.song.web;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.song.entity.TextMessage;
import com.song.utils.CheckUtil;
import com.song.utils.MessageUtil;
import com.song.utils.WxMenuUtils;

@Controller
public class entry {
	private static final String token = "songsong";
	private static final String URL_PREFIX = "http://1u5186s163.51mypc.cn";
	
	@RequestMapping(value="/index")
	public void index(HttpServletRequest request,HttpServletResponse response) throws IOException{
		// 接收微信服务器以Get请求发送的4个参数
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		try {
			String s = "{"
					+ "\"button\":["
								+ "{\"type\":\"view\",\"name\":\"个人信息\",\"url\":\""+URL_PREFIX+"/wechat/test/perinfo/\"},"
								+ "{\"name\":\"最新活动\","
											+ "\"sub_button\":["
															+ "{\"type\":\"view\",\"name\":\"幸运大转盘\",\"url\":\""+URL_PREFIX+"/wechat/test/activity1/\"},"
															+ "{\"type\":\"view\",\"name\":\"扫码抢红包\",\"url\":\""+URL_PREFIX+"/wechat/test/activity2/\"},"
															+ "{\"type\":\"view\",\"name\":\"测试微信登录\",\"url\":\""+URL_PREFIX+"/wechat/test/index/\"}"
								+ "]},"
								+ "{\"name\":\"小工具\","
											+ "\"sub_button\":["
															+ "{\"type\":\"scancode_waitmsg\",\"name\":\"扫一扫\",\"key\": \"rselfmenu_0_0\"},"
															+ "{\"type\":\"pic_sysphoto\",\"name\":\"拍照分享\",\"key\": \"rselfmenu_0_1\"}"
								+ "]}"
					+ "]}";
			String res = WxMenuUtils.createMenu(s, token);
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		   
		if (echostr != null && echostr.length() > 1) {  //验证
			if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
				   response.getWriter().print(echostr);        // 校验通过，原样返回echostr参数内容
			}
		}else{ //正常处理流程
			response.setContentType("text/xml;charset=utf-8");
	        try {
	            Map<String, String> map = MessageUtil.xmlToMap(request);
	            String toUserName = map.get("ToUserName");
	            String fromUserName = map.get("FromUserName");
	            String msgType = map.get("MsgType");
	            String content = map.get("Content");
	            
	            String message = null;
	            if ("text".equals(msgType)) {                // 对文本消息进行处理
	                TextMessage text = new TextMessage();
	                text.setFromUserName(toUserName);         // 发送和回复是反向的
	                text.setToUserName(fromUserName);
	                text.setMsgType("text");
	                text.setCreateTime(new Date().getTime());
	                text.setContent("你发送的消息是：" + content);
	                message = MessageUtil.textMessageToXML(text);
	                System.out.println(message);    
	                response.getWriter().write(message);                            // 将回应发送给微信服务器
	            }
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
}

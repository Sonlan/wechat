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
	
	
	@RequestMapping(value="/index")
	public void index(HttpServletRequest request,HttpServletResponse response) throws IOException{
		// 接收微信服务器以Get请求发送的4个参数
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		try {
			String s = "{\"button\":[{\"name\":\"休闲娱乐\",\"sub_button\":[{\"type\":\"click\",\"name\":\"笑话大全\",\"key\":\"m_joke\"},{\"type\":\"click\",\"name\":\"内涵段子\",\"key\":\"m_duanzi\"},{\"type\":\"click\",\"name\":\"爆笑图片\",\"key\":\"m_laughImg\"}]},{\"name\":\"实用工具\",\"sub_button\":[{\"type\":\"click\",\"name\":\"天气查询\",\"key\":\"m_weather\"},{\"type\":\"click\",\"name\":\"公交查询\",\"key\":\"m_bus\"},{\"type\":\"click\",\"name\":\"功能菜单\",\"key\":\"m_sysmenu\"}]},{\"name\":\"消息示例\",\"sub_button\":[{\"type\":\"click\",\"name\":\"关于企特\",\"key\":\"m_about\"},{\"type\":\"click\",\"name\":\"图文消息\",\"key\":\"m_imgmsg\"},{\"type\":\"click\",\"name\":\"音乐消息\",\"key\":\"m_musicmsg\"}]}]}";
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
	            }
	            response.getWriter().write(message);                            // 将回应发送给微信服务器
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
/*	@RequestMapping(value="/index")
	public void index1(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("utf-8");
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
            }
            response.getWriter().print(message);                            // 将回应发送给微信服务器
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	response.getWriter().close();
        }
    }*/
}

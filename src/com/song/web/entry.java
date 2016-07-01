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
		// ����΢�ŷ�������Get�����͵�4������
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		try {
			String s = "{\"button\":[{\"name\":\"��������\",\"sub_button\":[{\"type\":\"click\",\"name\":\"Ц����ȫ\",\"key\":\"m_joke\"},{\"type\":\"click\",\"name\":\"�ں�����\",\"key\":\"m_duanzi\"},{\"type\":\"click\",\"name\":\"��ЦͼƬ\",\"key\":\"m_laughImg\"}]},{\"name\":\"ʵ�ù���\",\"sub_button\":[{\"type\":\"click\",\"name\":\"������ѯ\",\"key\":\"m_weather\"},{\"type\":\"click\",\"name\":\"������ѯ\",\"key\":\"m_bus\"},{\"type\":\"click\",\"name\":\"���ܲ˵�\",\"key\":\"m_sysmenu\"}]},{\"name\":\"��Ϣʾ��\",\"sub_button\":[{\"type\":\"click\",\"name\":\"��������\",\"key\":\"m_about\"},{\"type\":\"click\",\"name\":\"ͼ����Ϣ\",\"key\":\"m_imgmsg\"},{\"type\":\"click\",\"name\":\"������Ϣ\",\"key\":\"m_musicmsg\"}]}]}";
			String res = WxMenuUtils.createMenu(s, token);
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		   
		if (echostr != null && echostr.length() > 1) {  //��֤
			if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
				   response.getWriter().print(echostr);        // У��ͨ����ԭ������echostr��������
			}
		}else{ //������������
			response.setContentType("text/xml;charset=utf-8");
	        try {
	            Map<String, String> map = MessageUtil.xmlToMap(request);
	            String toUserName = map.get("ToUserName");
	            String fromUserName = map.get("FromUserName");
	            String msgType = map.get("MsgType");
	            String content = map.get("Content");
	            
	            String message = null;
	            if ("text".equals(msgType)) {                // ���ı���Ϣ���д���
	                TextMessage text = new TextMessage();
	                text.setFromUserName(toUserName);         // ���ͺͻظ��Ƿ����
	                text.setToUserName(fromUserName);
	                text.setMsgType("text");
	                text.setCreateTime(new Date().getTime());
	                text.setContent("�㷢�͵���Ϣ�ǣ�" + content);
	                message = MessageUtil.textMessageToXML(text);
	                System.out.println(message);            
	            }
	            response.getWriter().write(message);                            // ����Ӧ���͸�΢�ŷ�����
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
            if ("text".equals(msgType)) {                // ���ı���Ϣ���д���
                TextMessage text = new TextMessage();
                text.setFromUserName(toUserName);         // ���ͺͻظ��Ƿ����
                text.setToUserName(fromUserName);
                text.setMsgType("text");
                text.setCreateTime(new Date().getTime());
                text.setContent("�㷢�͵���Ϣ�ǣ�" + content);
                message = MessageUtil.textMessageToXML(text);
                System.out.println(message);            
            }
            response.getWriter().print(message);                            // ����Ӧ���͸�΢�ŷ�����
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	response.getWriter().close();
        }
    }*/
}

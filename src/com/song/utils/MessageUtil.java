package com.song.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.song.entity.TextMessage;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * ʵ����Ϣ�ĸ�ʽת��(Map���ͺ�XML�Ļ�ת)
 */
public class MessageUtil {

    /**
     * ��XMLת����Map����
     */
    public static Map<String, String>xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
        
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();            // ʹ��dom4j����xml
        InputStream ins = request.getInputStream(); // ��request�л�ȡ������
        Document doc = reader.read(ins);
        
        Element root = doc.getRootElement();         // ��ȡ��Ԫ��
        List<Element> list = root.elements();        // ��ȡ���нڵ�
        
        for (Element e : list) {
            map.put(e.getName(), e.getText()); 
            System.out.println(e.getName() + "--->" + e.getText());
        }
        ins.close();
        return map;
    }
    
    /**
     * ���ı���Ϣ����ת����XML
     */
    public static String textMessageToXML(TextMessage textMessage){
        try {
        	XStream xstream = new XStream(new StaxDriver());              // ʹ��XStream��ʵ�����ʵ��ת����xml��ʽ    
            xstream.alias("xml", textMessage.getClass()); // ��xml��Ĭ�ϸ��ڵ��滻�ɡ�xml��
            return xstream.toXML(textMessage);
		} catch (Exception e) {
			return "��̨��������";
		}
        
    }
    
}

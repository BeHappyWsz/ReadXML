package sax;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import domain.Message;

/**
 * sax��ʽ����xml�ļ�
 * @author wsz
 * @date 2018��2��2��
 */
public class SAXTest {

	public static void main(String[] args) {
//		readXML();
		createXML();
	}

	static void readXML() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			SAXParserHandler handler = new SAXParserHandler();
			parser.parse("message.xml", handler);
			List<Message> list = handler.getList();
			for (Message message : list) {
				System.out.println(message.toString());
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ����������xml�ļ�
	 * @return
	 */
	static List<Message> parserXML() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParserHandler handler = null;
		try {
			SAXParser parser = factory.newSAXParser();
			handler = new SAXParserHandler();
			parser.parse("message.xml", handler);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return handler.getList();
	}
	
	static void createXML() {
		List<Message> msgList = parserXML();
		//����XML
		SAXTransformerFactory tff = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		try {
			TransformerHandler handler = tff.newTransformerHandler();
			//ͨ��handler���󴴽�Transformer���󣬲�����xml����
			Transformer tr = handler.getTransformer();
			tr.setOutputProperty(OutputKeys.ENCODING,"UTF-8");//���ñ���
			tr.setOutputProperty(OutputKeys.INDENT,"yes");//���û���
			File file = new File("newMsg.xml");
			if(!file.exists()) {
				file.createNewFile();
			}
			//����Result���󣬲�����handler����
			Result result = new StreamResult(new FileOutputStream(file));
			handler.setResult(result);
			
			//ʹ��handler��xml�ļ����ݱ�д:��-��д-�ر�
			handler.startDocument();
			AttributesImpl attr = new AttributesImpl();
			handler.startElement("", "", "messagess", attr);
			
			for(Message m : msgList) {
				//����msg-id�ڵ�
				attr.clear();
				attr.addAttribute("", "", "id", "", m.getId());
				handler.startElement("", "", "msg", attr);
				
				//����content�ڵ�
				//���Ϊ���򲻴����ڵ�
				if(m.getContent() != null && !"".equals(m.getContent().trim())) {
					attr.clear();
					handler.startElement("", "", "content", attr);
					handler.characters(m.getContent().toCharArray(), 0, m.getContent().length());
					handler.endElement("", "", "content");
				}
				
				//����user�ڵ�
				if(m.getUser() != null && !"".equals(m.getUser().trim())) {
					attr.clear();
					handler.startElement("", "", "user", attr);
					handler.characters(m.getUser().toCharArray(), 0, m.getUser().length());
					handler.endElement("", "", "user");
				}
				
				//����time�ڵ�
				if(m.getTime() != null && !"".equals(m.getTime().trim())) {
					attr.clear();
					handler.startElement("", "", "time", attr);
					handler.characters(m.getTime().toCharArray(), 0, m.getTime().length());
					handler.endElement("", "", "time");
				}
				
				handler.endElement("", "", "msg");
			}
			
			handler.endElement("", "", "messagess");
			handler.endDocument();
			
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

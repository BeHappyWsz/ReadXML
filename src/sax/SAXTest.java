package sax;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import domain.Message;

/**
 * sax方式解析xml文件
 * @author wsz
 * @date 2018年2月2日
 */
public class SAXTest {

	public static void main(String[] args) {
		readXML();
	}

	static void readXML() {
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			SAXParserHandler handler = new SAXParserHandler();
			try {
				parser.parse("message.xml", handler);
				List<Message> list = handler.getList();
				for (Message message : list) {
					System.out.println(message.toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
	}
}

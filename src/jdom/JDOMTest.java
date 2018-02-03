package jdom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import domain.Message;

/**
 * ʹ��jdom��ȡxml
 * ��Ҫ����jdom.jar
 * @author wsz
 * @date 2018��2��2��
 */
public class JDOMTest {

	public static void main(String[] args) {
//		readXML();
		createXML();
	}

	static List<Message> list = new ArrayList<Message>();
	
	static void readXML() {
		//1.SAXBuilder����
		SAXBuilder saxBuilder = new SAXBuilder();
		try {
			//2.����������
			Document document = saxBuilder.build(new FileInputStream("message.xml"));
			//3.��ȡ�ļ����ڵ�
			Element rootElement = document.getRootElement();
			//4.��ȡ���ڵ��µ��ӽڵ�
			List<Element> msgList = rootElement.getChildren();
			for(Element msg : msgList) {
				Message message = new Message();
				System.out.println("��ʼ������"+msgList.indexOf(msg)+"��msg");
				
				List<Attribute> attributes = msg.getAttributes();
				for(Attribute attr : attributes) {
					String name = attr.getName();
					String value = attr.getValue();
					System.out.println("������:"+name+"  ����ֵ:"+value);
					if("id".equals(name)) {
						message.setId(value);
					}
				}
				List<Element> children = msg.getChildren();
				for(Element child : children) {
					String name = child.getName();
					String value = child.getValue();
					System.out.println("�ڵ���:"+name+"  �ڵ�ֵ:"+value);
					
					if("content".equals(name)) {
						message.setContent(value);
					}else if("user".equals(name)) {
						message.setUser(value);
					}else if("time".equals(name)) {
						message.setTime(value);
					}
				}
				list.add(message);
				System.out.println("����������"+msgList.indexOf(msg)+"��msg");
			}
			for(Message m:list) {
				System.out.println(m.toString());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void createXML() {
		//1.���ڵ�-�ڵ�����
		Element messagess = new Element("messagess");
		messagess.setAttribute("version","2.0");
		//2.document����
		Document document = new Document(messagess);
		//msg�ڵ�
		Element msg = new Element("msg");
		msg.setAttribute("id", "1");
		messagess.addContent(msg);
		//msg�ڵ��ڲ�����-content
		Element content = new Element("content");
		content.setText("<script/>hoias<vixushw/?>.saudhiausd");
		msg.addContent(content);
		
		//3.XMLOutputter����-��document����ת��Ϊxml�ĵ�
		Format format = Format.getCompactFormat();
		format.setIndent("");//���û���
		format.setEncoding("UTF-8");//����
		//ת������??
		XMLOutputter outputer = new XMLOutputter(format);
		try {
			outputer.output(document, new FileOutputStream(new File("jdom.xml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

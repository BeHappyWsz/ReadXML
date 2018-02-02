package jdom;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import domain.Message;

/**
 * 使用jdom读取xml
 * 需要导入jdom.jar
 * @author wsz
 * @date 2018年2月2日
 */
public class JDOMTest {

	public static void main(String[] args) {
		readXML();
	}

	static List<Message> list = new ArrayList<Message>();
	
	static void readXML() {
		//1.SAXBuilder对象
		SAXBuilder saxBuilder = new SAXBuilder();
		try {
			//2.加载输入流
			Document document = saxBuilder.build(new FileInputStream("message.xml"));
			//3.获取文件根节点
			Element rootElement = document.getRootElement();
			//4.获取根节点下的子节点
			List<Element> msgList = rootElement.getChildren();
			for(Element msg : msgList) {
				Message message = new Message();
				System.out.println("开始解析第"+msgList.indexOf(msg)+"条msg");
				
				List<Attribute> attributes = msg.getAttributes();
				for(Attribute attr : attributes) {
					String name = attr.getName();
					String value = attr.getValue();
					System.out.println("属性名:"+name+"  属性值:"+value);
					if("id".equals(name)) {
						message.setId(value);
					}
				}
				List<Element> children = msg.getChildren();
				for(Element child : children) {
					String name = child.getName();
					String value = child.getValue();
					System.out.println("节点名:"+name+"  节点值:"+value);
					
					if("content".equals(name)) {
						message.setContent(value);
					}else if("user".equals(name)) {
						message.setUser(value);
					}else if("time".equals(name)) {
						message.setTime(value);
					}
				}
				list.add(message);
				System.out.println("结束解析第"+msgList.indexOf(msg)+"条msg");
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
}

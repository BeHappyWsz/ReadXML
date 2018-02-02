package dom;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * dom方式解析xml文件
 * @author wsz
 * @date 2018年2月2日
 */
public class DOMTest {

	public static void main(String[] args) {
		readXML();
	}

	static void readXML(){
		//1.创建DocumentBuilderFactory对象
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			//2.创建DocumentBuilder对象
			DocumentBuilder db = dbf.newDocumentBuilder();
			//3.获取文件
			Document document = db.parse("message.xml");
			//4.获取所有msg节点的结合
			NodeList msgList = document.getElementsByTagName("msg");
			//5.遍历节点集合
			for (int i = 0; i < msgList.getLength(); i++) {
				Node msg = msgList.item(i);
				//不知道节点的属性信息
//				NamedNodeMap attrs = msg.getAttributes();
//				for (int j = 0; j < attrs.getLength(); j++) {
//					//8.item(i)获取具体属性,从0开始
//					Node attr = attrs.item(j);
//					String nodeName = attr.getNodeName();
//					String nodeValue = attr.getNodeValue();
//					System.out.println(nodeName+"_"+nodeValue);
//				}
				//知道节点的属性信息
//				Element message =(Element)msg;
//				String attribute = message.getAttribute("id");
//				System.out.println("id属性的值为:"+attribute);
				
				//解析子节点
				NodeList childNodes = msg.getChildNodes();
				int length = childNodes.getLength();
				System.out.println("子节点数量(前后):"+length);
				for (int j = 0; j < length; j++) {
					if(childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
						System.out.print("节点名:"+childNodes.item(j).getNodeName());
						System.out.print(" (固定为null):"+childNodes.item(j).getNodeValue());
						System.out.print(" 节点值(若仍有子节点):"+childNodes.item(j).getFirstChild().getNodeValue());
						System.out.println(" 节点值:"+childNodes.item(j).getTextContent());
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

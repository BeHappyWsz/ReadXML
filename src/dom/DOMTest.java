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
 * dom��ʽ����xml�ļ�
 * @author wsz
 * @date 2018��2��2��
 */
public class DOMTest {

	public static void main(String[] args) {
		readXML();
	}

	static void readXML(){
		//1.����DocumentBuilderFactory����
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			//2.����DocumentBuilder����
			DocumentBuilder db = dbf.newDocumentBuilder();
			//3.��ȡ�ļ�
			Document document = db.parse("message.xml");
			//4.��ȡ����msg�ڵ�Ľ��
			NodeList msgList = document.getElementsByTagName("msg");
			//5.�����ڵ㼯��
			for (int i = 0; i < msgList.getLength(); i++) {
				Node msg = msgList.item(i);
				//��֪���ڵ��������Ϣ
//				NamedNodeMap attrs = msg.getAttributes();
//				for (int j = 0; j < attrs.getLength(); j++) {
//					//8.item(i)��ȡ��������,��0��ʼ
//					Node attr = attrs.item(j);
//					String nodeName = attr.getNodeName();
//					String nodeValue = attr.getNodeValue();
//					System.out.println(nodeName+"_"+nodeValue);
//				}
				//֪���ڵ��������Ϣ
//				Element message =(Element)msg;
//				String attribute = message.getAttribute("id");
//				System.out.println("id���Ե�ֵΪ:"+attribute);
				
				//�����ӽڵ�
				NodeList childNodes = msg.getChildNodes();
				int length = childNodes.getLength();
				System.out.println("�ӽڵ�����(ǰ��):"+length);
				for (int j = 0; j < length; j++) {
					if(childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
						System.out.print("�ڵ���:"+childNodes.item(j).getNodeName());
						System.out.print(" (�̶�Ϊnull):"+childNodes.item(j).getNodeValue());
						System.out.print(" �ڵ�ֵ(�������ӽڵ�):"+childNodes.item(j).getFirstChild().getNodeValue());
						System.out.println(" �ڵ�ֵ:"+childNodes.item(j).getTextContent());
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

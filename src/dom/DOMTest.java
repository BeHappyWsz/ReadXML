package dom;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * dom��ʽ����xml�ļ�
 * @author wsz
 * @date 2018��2��2��
 */
public class DOMTest {

	public static void main(String[] args) {
//		readXML();
		
		JSONArray ja = new JSONArray();
		for(int i = 0;i <10;i++) {
			JSONObject jo = new JSONObject();
			jo.put("id", i);
			jo.put("content", i);
			jo.put("user", i);
			jo.put("time", i);
			ja.add(jo);
		}
		createXML(ja);
	}

	static void readXML(){
		//1.����DocumentBuilderFactory����
		try {
			//2.����DocumentBuilder����
			DocumentBuilder db = getDocumentBuilder();
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
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ����XML�ļ���������������
	 * @param ja
	 */
	static void createXML(JSONArray ja) {
		DocumentBuilder db = getDocumentBuilder();
		Document document = db.newDocument();
		document.setXmlStandalone(true);//ȥ��xml�ļ��е�standalone����
		setMsg(document,ja);//�����������
		TransformerFactory tff = TransformerFactory.newInstance();
		try {
			//����Transformer����,��������dom��
			Transformer tf = tff.newTransformer();
			tf.setOutputProperty(OutputKeys.INDENT,"yes");
			//����xml�ļ�
			tf.transform(new DOMSource(document), new StreamResult(new File("messagess1.xml")));
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ģ�������������
	 * @param document
	 * @param ja
	 */
	static void setMsg(Document document,JSONArray ja) {
		//��Ӹ��ڵ�
		Element messagess = document.createElement("messagess");
		
		//����msg�ڵ㲢���messagess���ڵ���
		for (Object jo : ja) {
			Element msg = document.createElement("msg");
			JSONObject obj = (JSONObject) jo;
			Set<String> keySet = obj.keySet();
			for(String str : keySet) {
				if("id".equals(str)) {
					msg.setAttribute(str, obj.getString(str));
					continue;
				}
				Element elem = document.createElement(str);
				elem.setTextContent(obj.getString(str));
				msg.appendChild(elem);
			}
			messagess.appendChild(msg);
		}
		document.appendChild(messagess);
		
//		//��Ӹ��ڵ�
//		Element messagess = document.createElement("messagess");
//		//��msg�ڵ����messagess���ڵ���
//		Element msg = document.createElement("msg");
//		
//		Element content = document.createElement("content");
//		content.setTextContent("����3");
//		msg.appendChild(content);
//		
//		Element user = document.createElement("user");
//		user.setTextContent("�û�3");
//		msg.appendChild(user);
//		
//		Element time = document.createElement("time");
//		time.setTextContent("2018-2-3");
//		msg.appendChild(time);
//		
//		msg.setAttribute("id", "3");
//		messagess.appendChild(msg);
//		//��messagess�ڵ�(�Ѿ�����msg)��ӵ�dom����
//		document.appendChild(messagess);
	}
	
	/**
	 * ��ȡDocumentBuilder
	 * @return
	 */
	static DocumentBuilder getDocumentBuilder() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return db;
	}
	
}

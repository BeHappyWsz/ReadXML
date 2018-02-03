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
 * dom方式解析xml文件
 * @author wsz
 * @date 2018年2月2日
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
		//1.创建DocumentBuilderFactory对象
		try {
			//2.创建DocumentBuilder对象
			DocumentBuilder db = getDocumentBuilder();
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
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成XML文件并传入批量数据
	 * @param ja
	 */
	static void createXML(JSONArray ja) {
		DocumentBuilder db = getDocumentBuilder();
		Document document = db.newDocument();
		document.setXmlStandalone(true);//去除xml文件中的standalone属性
		setMsg(document,ja);//添加批量数据
		TransformerFactory tff = TransformerFactory.newInstance();
		try {
			//创建Transformer对象,用于生成dom树
			Transformer tf = tff.newTransformer();
			tf.setOutputProperty(OutputKeys.INDENT,"yes");
			//生成xml文件
			tf.transform(new DOMSource(document), new StreamResult(new File("messagess1.xml")));
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 模拟添加批量数据
	 * @param document
	 * @param ja
	 */
	static void setMsg(Document document,JSONArray ja) {
		//添加根节点
		Element messagess = document.createElement("messagess");
		
		//遍历msg节点并添加messagess根节点中
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
		
//		//添加根节点
//		Element messagess = document.createElement("messagess");
//		//将msg节点添加messagess根节点中
//		Element msg = document.createElement("msg");
//		
//		Element content = document.createElement("content");
//		content.setTextContent("内容3");
//		msg.appendChild(content);
//		
//		Element user = document.createElement("user");
//		user.setTextContent("用户3");
//		msg.appendChild(user);
//		
//		Element time = document.createElement("time");
//		time.setTextContent("2018-2-3");
//		msg.appendChild(time);
//		
//		msg.setAttribute("id", "3");
//		messagess.appendChild(msg);
//		//将messagess节点(已经包含msg)添加到dom树中
//		document.appendChild(messagess);
	}
	
	/**
	 * 获取DocumentBuilder
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

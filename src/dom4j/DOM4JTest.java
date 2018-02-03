package dom4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * ʹ��dom4j��ȡxml
 * ��Ҫ����dom4j.jar
 * @author wsz
 * @date 2018��2��2��
 */
public class DOM4JTest {

	public static void main(String[] args) {
//		readXML();
		
		JSONArray ja = new JSONArray();
		for(int i = 10;i <30;i++) {
			JSONObject jo = new JSONObject();
			jo.put("id", i);
			jo.put("content", i);
			jo.put("user", i);
			jo.put("time", "<!iuga>><ujsya?<script/>");
			ja.add(jo);
		}
		createXML(ja);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static void readXML() {
		//����xml�ļ�
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File("message.xml"));
			//��ȡ���ڵ�messagess
			Element messagess = document.getRootElement();
			Iterator it = messagess.elementIterator();
			
			while(it.hasNext()) {
				System.out.println("��ʼ����һ��msg");
				Element msg = (Element)it.next();
				List<Attribute> attributes = msg.attributes();
				for (Attribute attr : attributes) {
					String name = attr.getName();
					String value = attr.getValue();
					System.out.println("�ڵ���:"+name+" �ڵ�ֵ:"+value);
				}
				Iterator itt = msg.elementIterator();
				while(itt.hasNext()) {
					Element child = (Element) itt.next();
					String name = child.getName();
					String value = child.getStringValue();
					System.out.println("������:"+name+" ����ֵ:"+value);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	static void createXML(JSONArray ja) {
		Document document = DocumentHelper.createDocument();
		
		setMsg(document,ja);
		//����xml���ɵĸ�ʽ-����xmlwriter
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");//���ñ��룬Ĭ��ΪUTF-8
		//����xml�ļ�
		File file = new File("newFile.xml");
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileOutputStream(file),format);
			writer.setEscapeText(false);//�����Ƿ�ת��,Ĭ��true,�Զ�ת��
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ģ�������������
	 * @param document
	 * @param ja
	 */
	static void setMsg(Document document, JSONArray ja) {
		//����xml�ļ����ڵ�rss���������
		Element messagess = document.addElement("messagess");
		messagess.addAttribute("version", "2.0");
		JSONObject jo;
		Element msg;
		Element content;
		for(Object obj : ja) {
			jo = (JSONObject)obj;
			msg = messagess.addElement("msg");
			Set<String> keySet = jo.keySet();
			for (String str : keySet) {
				if("id".equals(str)) {
					msg.addAttribute(str, jo.getString(str));
				}else {
					content = msg.addElement(str);
					content.setText(jo.getString(str));
				}
			}
		}
	}
}

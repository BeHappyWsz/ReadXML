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
 * 使用dom4j读取xml
 * 需要导入dom4j.jar
 * @author wsz
 * @date 2018年2月2日
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
		//解析xml文件
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File("message.xml"));
			//获取根节点messagess
			Element messagess = document.getRootElement();
			Iterator it = messagess.elementIterator();
			
			while(it.hasNext()) {
				System.out.println("开始遍历一条msg");
				Element msg = (Element)it.next();
				List<Attribute> attributes = msg.attributes();
				for (Attribute attr : attributes) {
					String name = attr.getName();
					String value = attr.getValue();
					System.out.println("节点名:"+name+" 节点值:"+value);
				}
				Iterator itt = msg.elementIterator();
				while(itt.hasNext()) {
					Element child = (Element) itt.next();
					String name = child.getName();
					String value = child.getStringValue();
					System.out.println("属性名:"+name+" 属性值:"+value);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	static void createXML(JSONArray ja) {
		Document document = DocumentHelper.createDocument();
		
		setMsg(document,ja);
		//设置xml生成的格式-传入xmlwriter
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");//设置编码，默认为UTF-8
		//生成xml文件
		File file = new File("newFile.xml");
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileOutputStream(file),format);
			writer.setEscapeText(false);//设置是否转移,默认true,自动转译
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 模拟批量添加数据
	 * @param document
	 * @param ja
	 */
	static void setMsg(Document document, JSONArray ja) {
		//创建xml文件根节点rss并添加属性
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

package dom4j;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 使用dom4j读取xml
 * 需要导入dom4j.jar
 * @author wsz
 * @date 2018年2月2日
 */
public class DOM4JTest {

	public static void main(String[] args) {
		readXML();
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
}

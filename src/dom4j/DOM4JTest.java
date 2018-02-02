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
 * ʹ��dom4j��ȡxml
 * ��Ҫ����dom4j.jar
 * @author wsz
 * @date 2018��2��2��
 */
public class DOM4JTest {

	public static void main(String[] args) {
		readXML();
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
}

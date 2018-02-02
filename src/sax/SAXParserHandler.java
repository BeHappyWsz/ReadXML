package sax;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import domain.Message;

public class SAXParserHandler extends DefaultHandler{
	int index = 0;
	
	String value = null;
	Message message=null;
	List<Message> list = new ArrayList<>();
	
	public List<Message> getList() {
		return list;
	}
	
	/**
	 * ����/����xml��ǩ
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		//��ʼ����
		if("msg".equals(qName)) {
			//���������Message����
			message = new Message();
			
			index++;
			System.out.println("��ʼ�������:"+index);
			//��֪�ڵ���Ԫ������
//			String value = attributes.getValue("id");
//			System.out.println(value);
			
			//δ֪�ڵ���Ԫ������
			int length = attributes.getLength();
			for(int i=0;i<length;i++) {
				String name = attributes.getQName(i);
				String value = attributes.getValue(i);
				System.out.print("������:"+name);
				System.out.println(" ����ֵ:"+value);
				
				if("id".equals(name)) {//������һ��msg
					message.setId(value);
				}
			}
		}else if(!"msg".equals(qName) && !"messagess".equals(qName)) {
			//�ڵ�
			System.out.print(qName);
		}
		
	}
	/**
	 * ��ȡ�ڵ������
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		value = new String(ch,start,length);//��ʱ����ȫ�ո��ַ���,����ʵ����󱣴�
		if(!value.trim().equals("")) {
			System.out.println("  "+value);
		}
	}
	
	/**
	 * ����������ǩ
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		//�ж��Ƿ�һ��msgɨ�����
		if("msg".equals(qName)) {
			list.add(message);
			message = null;
			
			System.out.println("�����������:"+index);
		}else if("content".equals(qName)) {//��ʼ��ֵ
			message.setContent(value);
		}else if("user".equals(qName)) {
			message.setUser(value);
		}else if("time".equals(qName)) {
			message.setTime(value);
		}
	}
	
	/**
	 * ������ʼ
	 */
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		System.out.println("SAX������ʼ");
	}
	
	/**
	 * ��������
	 */
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		System.out.println("SAX��������");
	}
}

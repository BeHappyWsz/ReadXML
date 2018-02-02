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
	 * 解析/遍历xml标签
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		//开始解析
		if("msg".equals(qName)) {
			//创建保存的Message对象
			message = new Message();
			
			index++;
			System.out.println("开始遍历序号:"+index);
			//已知节点下元素属性
//			String value = attributes.getValue("id");
//			System.out.println(value);
			
			//未知节点下元素属性
			int length = attributes.getLength();
			for(int i=0;i<length;i++) {
				String name = attributes.getQName(i);
				String value = attributes.getValue(i);
				System.out.print("属性名:"+name);
				System.out.println(" 属性值:"+value);
				
				if("id".equals(name)) {//遍历到一条msg
					message.setId(value);
				}
			}
		}else if(!"msg".equals(qName) && !"messagess".equals(qName)) {
			//节点
			System.out.print(qName);
		}
		
	}
	/**
	 * 获取节点的内容
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		value = new String(ch,start,length);//此时包含全空格字符串,用于实体对象保存
		if(!value.trim().equals("")) {
			System.out.println("  "+value);
		}
	}
	
	/**
	 * 遍历结束标签
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		//判断是否一条msg扫描结束
		if("msg".equals(qName)) {
			list.add(message);
			message = null;
			
			System.out.println("结束遍历序号:"+index);
		}else if("content".equals(qName)) {//开始设值
			message.setContent(value);
		}else if("user".equals(qName)) {
			message.setUser(value);
		}else if("time".equals(qName)) {
			message.setTime(value);
		}
	}
	
	/**
	 * 解析开始
	 */
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		System.out.println("SAX解析开始");
	}
	
	/**
	 * 解析结束
	 */
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		System.out.println("SAX解析结束");
	}
}

package com.test.utilsBackUp.xml;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.test.utilsBackUp.other.StringUtil;

/**
 * @Description 将xml字符串转换为java对象的工具类 
 * @author  
 * @date  
 */
public class XmlToObjectUtil {
	private Map<String, Object> xmlNodes = new HashMap<String, Object>();
	/**
	 * 获取工具类实例
	 * @return
	 */
	public static XmlToObjectUtil createUtil(){
		return new XmlToObjectUtil();
	}
	/**
	 * 将xml转换为class对象
	 * @param xml 字符串格式的xml
	 * @param object 要转换成对象
	 * @return 转换后的对象
	 */
	public Object parseXmlToObject(String xml, Object object){
		parseXmlToNodes(xml);
		invokeObject(object);
		return object;
	}
	
	/**
	 * 将字符串的xml解析出来，将节点信息存放在Map中
	 * @param xml	字符串格式的xml
	 */
	private void parseXmlToNodes(String xml){
		try {
			Document document = DocumentHelper.parseText(xml).getDocument();
			addElementNode(document.getRootElement());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将xml节点map中的属性设置到对象属性中去
	 * @param object 要设置的对象
	 */
	private void invokeObject(Object object){
		Method[] methods = object.getClass().getMethods();
		for (Method method : methods) {
			this.invokeMethod(method, object);
		}
	}
	/**
	 * 将xml节点map中的属性设置到对象属性中去
	 * @param method 要设置的方法
	 * @param object 要设置的对象
	 * @throws Exception
	 */
	private void invokeMethod(Method method, Object object){
		try {
			if(!isCanSetValue(method)){
				return;
			}
			String methodName = method.getName();
			String filedName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
			Object value = xmlNodes.get(filedName);
			if(value != null){
				method.invoke(object, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断method是否正常的setValue方法
	 * @param method	当前方法
	 * @return	真
	 */
	private boolean isCanSetValue(Method method){
		String methodName = method.getName();
		return methodName.startsWith("set") && method.getParameterTypes().length == 1;
	}
	/**
	 * 循环解析xml节点添加到节点map中
	 * 第一步先解析当前Element有没有属性，有则将属性也添加到节点map中
	 * 第二步验证当前Element有没有子节点，没有子节点就取出当前节点的text，不为空就添加到节点map中
	 * 有子节点的就循环遍历
	 * @param parentElement	要处理的Element
	 */
	private void addElementNode(Element parentElement){
		addAttributeNode(parentElement);
		List<?> childrenElements = parentElement.elements();
		if(childrenElements.isEmpty()){
			String text = parentElement.getTextTrim();
			if(StringUtil.isNotBlank(text)){
				xmlNodes.put(parentElement.getName(), text);
			}
		}else{
			for (Iterator<?> iterator = childrenElements.iterator(); iterator.hasNext();) {
				Element childElement = (Element) iterator.next();
				addElementNode(childElement);
			}
		}
	}
	/**
	 * 添加Element节点的Attribute到节点信息map中
	 * @param element	节点
	 */
	private void addAttributeNode(Element element){
		List<?> list = element.attributes();
		if(list.isEmpty()){
			return;
		}
		for (Object object : list) {
			Attribute attribute = (Attribute)object;
			xmlNodes.put(attribute.getName(), attribute.getValue());
		}
	}
}

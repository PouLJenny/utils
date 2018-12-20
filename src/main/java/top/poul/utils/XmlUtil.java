package top.poul.utils;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * xml <--> bean
 * 
 * @author Poul.Y
 * @since 2017年8月22日 上午11:23:04
 */
public class XmlUtil {

	private XmlUtil() {

	}

	@SuppressWarnings("unchecked")
	public static <T> T XMLStringToBean(String xml, Class<T> Object) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Object);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (T) unmarshaller.unmarshal(new StringReader(xml));
	}

	/**
	 * 把对象转换成 xml
	 * 
	 * @param obj
	 * @param encoding
	 * @return
	 * @throws JAXBException 
	 */
	public static String convertToXml(Object obj, String encoding, boolean format) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, format);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
		StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		String result = writer.toString();
		return result;
	}

	/**
	 * 翻译xml中的所有的实体字符
	 * @param xml
	 * @return
	 */
	public static String tanslateCharacterEntities (String xml) {
		xml = xml.replaceAll("&ensp;"," ");
		xml = xml.replaceAll("&emsp;"," ");
		xml = xml.replaceAll("&nbsp;"," ");
		xml = xml.replaceAll("&lt;","<");
		xml = xml.replaceAll("&gt;",">");
		xml = xml.replaceAll("&amp;","&");
		xml = xml.replaceAll("&quot;","\"");

		return xml;
	}
}

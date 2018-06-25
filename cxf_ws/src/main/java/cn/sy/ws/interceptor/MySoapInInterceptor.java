package cn.sy.ws.interceptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import cn.sy.ws.util.WebServiceUtil;

/**
 * SOAP接收拦截器
 * 获取SOAP消息内容XML
 * @author Administrator
 *
 */
public class MySoapInInterceptor extends AbstractSoapInterceptor {
	private static Logger logger = LoggerFactory.getLogger(MySoapInInterceptor.class);
	
	public MySoapInInterceptor() {
		super(Phase.USER_STREAM);
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {

//		logger.debug("MySoapInterceptor (extends AbstractSoapInterceptor) message: \n" + message);

//		Set<Class<?>> formats = message.getContentFormats();
//		for(Class<?> clazz : formats) {
//			logger.debug("   formats:" + clazz.getName());
//		}
		// org.apache.cxf.io.DelegatingInputStream and java.io.InputStream
		
		InputStream is = message.getContent(InputStream.class);
		String str=null;
		
		try {
			// 获取报文内容，转换为字符串
			str = IOUtils.toString(is, "utf-8");
//			logger.debug("payload:\n" + str);
			
			String bodyStr = getSoapBodyXmlStr(str);
			logger.debug("body xml:\n" + bodyStr);
			
			// 由于前面获取报文会消耗InputStream，所以这里转换回InputStream，放回message
			is = new ByteArrayInputStream(str.getBytes("UTF-8"));
			message.setContent(InputStream.class, is);

		} catch (IOException e) {
			e.printStackTrace();
		}
                
	}

	private String getSoapBodyXmlStr(String soapXmlStr) {
		String result = null;
		try {
//			Document doc = WebServiceUtil.xmlToDocument(soapXmlStr);
			InputStream is = new ByteArrayInputStream(soapXmlStr.getBytes());
			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage(
					new MimeHeaders(), is);
//			QName q = new QName("Body");
//			result = WebServiceUtil.getChildXmlFromSOAPMessage(soapMessage, q);
			
			Element rootElement = cn.sy.ws.util.WebServiceUtil.getFirstChildElement(soapMessage.getSOAPBody());
			
			result = WebServiceUtil.nodeToXml(rootElement);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SOAPException e) {

			e.printStackTrace();
		} catch (TransformerException e) {

			e.printStackTrace();
		}
		
		return result;
	}
}

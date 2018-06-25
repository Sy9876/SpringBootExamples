package cn.sy.ws.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * webservice util
 * @author Administrator
 *
 */
public class WebServiceUtil {

	private static Logger logger = LoggerFactory.getLogger(WebServiceUtil.class);
	
	private static final String DEFAULT_NS_URI="http://webService.sy.cn/";
	private static final String DEFAULT_NS_PREFIX="web";
	private static final String DEFAULT_REQUEST_ROOT_ELEMENT="invoke";
	private static final String DEFAULT_RESPONSE_ROOT_ELEMENT="invokeResponse";

	private static String nsUri = DEFAULT_NS_URI;
	private static String nsPrefix = DEFAULT_NS_PREFIX;
	private static String nsRequestRootElement = DEFAULT_REQUEST_ROOT_ELEMENT;
	private static String nsResponseRootElement = DEFAULT_RESPONSE_ROOT_ELEMENT;
	
	/**
	 * 根据xml字符串创建请求SoapMessage
	 * SOAPEnvelope中增加namespace声明
	 * SoapBody的根元素为 invoke，xmlStr添加到invoke元素中
	 * 
	 * @param xmlStr
	 * @return
	 * @throws SOAPException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static SOAPMessage createRequestSoapMessage(String xmlStr)
			throws SOAPException, ParserConfigurationException, SAXException, IOException {
		QName q = new QName(nsUri,
				nsRequestRootElement,
				nsPrefix);
		
		return createSoapMessage(xmlStr, q);
	}
	
	/**
	 * 创建无报文的应答SoapMessage
	 * SOAPEnvelope中增加namespace声明
	 * SoapBody的根元素为invokeResponse
	 * 
	 * @return
	 * @throws SOAPException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static SOAPMessage createEmptyResponseSoapMessage()
			throws SOAPException, ParserConfigurationException, SAXException, IOException {
		QName soapBodyRoot = new QName(nsUri,
				nsResponseRootElement,
				nsPrefix);
		
		MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPEnvelope envelope =  soapMessage.getSOAPPart().getEnvelope();
        // 在envelope中增加ns
        String soapBodyRootNs = soapBodyRoot.getPrefix();
		String soapBodyRootURI = soapBodyRoot.getNamespaceURI();
		envelope.addNamespaceDeclaration(soapBodyRootNs, soapBodyRootURI);
		// 把soapBodyRoot作为soap body的子元素
        SOAPBody body = envelope.getBody();
        SOAPBodyElement sbe = body.addBodyElement(soapBodyRoot);
        
		return soapMessage;
	}
	
	/**
	 * 根据xml字符串创建应答SoapMessage
	 * SOAPEnvelope中增加namespace声明
	 * SoapBody的根元素为invokeResponse，xmlStr添加到invokeResponse元素中
	 * 
	 * @param xmlStr
	 * @return
	 * @throws SOAPException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static SOAPMessage createResponseSoapMessage(String xmlStr)
			throws SOAPException, ParserConfigurationException, SAXException, IOException {
		QName q = new QName(nsUri,
				nsResponseRootElement,
				nsPrefix);
		
		return createSoapMessage(xmlStr, q);
	}

	/**
	 * 根据xml字符串创建SoapMessage
	 * 根据参数soapBodyRoot，在SOAPEnvelope中增加namespace声明
	 * SoapBody的根元素为参数soapBodyRoot，xmlStr添加到soapBodyRoot元素中
	 * @param xmlStr
	 * @param soapBodyRoot 为soap body根元素的限定名。
	 * @return
	 * @throws SOAPException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static SOAPMessage createSoapMessage(String xmlStr, QName soapBodyRoot)
			throws SOAPException, ParserConfigurationException, SAXException, IOException {

        // xmlStr转换为Document
        Document doc = xmlToDocument(xmlStr);

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPEnvelope envelope =  soapMessage.getSOAPPart().getEnvelope();
        // 在envelope中增加ns
        String soapBodyRootNs = soapBodyRoot.getPrefix();
		String soapBodyRootURI = soapBodyRoot.getNamespaceURI();
		envelope.addNamespaceDeclaration(soapBodyRootNs, soapBodyRootURI);
		// 把soapBodyRoot作为soap body的子元素
        SOAPBody body = envelope.getBody();
        SOAPBodyElement sbe = body.addBodyElement(soapBodyRoot);
        
        // xml加入soap message
        appendDocument(soapMessage, sbe, doc);

        logger.debug("createSoapMessage(String, QName). message is:\n" + soapMessageToString(soapMessage));
		
		return soapMessage;
	}

	/**
	 * 创建SoapMessage。以参数doc作为soap body的子元素
	 * @param doc
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 * @throws SOAPException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static SOAPMessage createSOAPMessage(Document doc)
			throws JAXBException, IOException, SOAPException, SAXException, ParserConfigurationException {

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();
        SOAPEnvelope envelope =  message.getSOAPPart().getEnvelope();
        SOAPBody body = envelope.getBody();
        
        body.addDocument(doc);
        
        logger.debug("createSoapMessage(Document) message is: \n" + soapMessageToString(message));

        return message;
	}

	

	/**
	 * 解析SOAPMessage，获取invokeResponse的子节点。
	 * @param soapMessage
	 * @return
	 * @throws SOAPException
	 * @throws TransformerException
	 */
	public static Node getInvokeResponseNode(SOAPMessage soapMessage)
			throws SOAPException, TransformerException {

		QName defaultSoapBodyRoot = new QName(nsUri,
				nsResponseRootElement,
				nsPrefix);
		
		Node invokeResponseNode = getChildNodeFromSOAPMessage(soapMessage, defaultSoapBodyRoot);
		
		return invokeResponseNode;
	}
	
	/**
	 * 解析SOAPMessage，获取invokeResponse的子节点，转换为字符串返回。
	 * @param soapMessage
	 * @return
	 * @throws SOAPException
	 * @throws TransformerException
	 */
	public static String getInvokeResponseXml(SOAPMessage soapMessage)
			throws SOAPException, TransformerException {
		String xmlStr = null;
		
		Node invokeResponseNode = getInvokeResponseNode(soapMessage);
		xmlStr = nodeToXml(invokeResponseNode);
		logger.debug("getInvokeResponseXml.\n" + xmlStr);
		
		return xmlStr;
	}
	
	
	/**
	 * 解析SOAPMessage，获取invoke的子节点
	 * @param soapMessage
	 * @return
	 * @throws SOAPException
	 * @throws TransformerException
	 */
	public static Node getInvokeNode(SOAPMessage soapMessage)
			throws SOAPException, TransformerException {

		QName defaultSoapBodyRoot = new QName(nsUri,
				nsRequestRootElement,
				nsPrefix);
		
		Node invokeRequestNode = getChildNodeFromSOAPMessage(soapMessage, defaultSoapBodyRoot);
		
		return invokeRequestNode;
	}
	
	/**
	 * 解析SOAPMessage，获取invoke的子节点，转换为字符串返回。
	 * @param soapMessage
	 * @return
	 * @throws SOAPException
	 * @throws TransformerException
	 */
	public static String getInvokeXml(SOAPMessage soapMessage)
			throws SOAPException, TransformerException {

		String xmlStr = null;
		
		Node invokeNode = getInvokeNode(soapMessage);
		xmlStr = nodeToXml(invokeNode);
		logger.debug("getInvokeXml.\n" + xmlStr);
		
		return xmlStr;
	}

	
	/**
	 * SOAPMessage转换为字符串
	 * @param message
	 * @return
	 * @throws SOAPException
	 * @throws IOException
	 */
	public static String soapMessageToString(SOAPMessage message) throws SOAPException, IOException {
		String encoding = "UTF-8";
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        message.writeTo(bao);
        return bao.toString(encoding);
	}



	/**
	 * 把Document添加到SOAPMessage的元素中。
	 * srcDoc是一个Document对象，要把这个对象添加到appendToMessage中的appendToElement里面。
	 * 返回appendToElement
	 * @param appendToMessage 目标SOAPMessage
	 * @param appendToElement 目标SOAPBodyElement
	 * @param srcDoc 源Document
	 * @return appendToElement
	 */
	public static SOAPBodyElement appendDocument(SOAPMessage appendToMessage, SOAPBodyElement appendToElement, Document srcDoc) {
		SOAPPart soapPart = appendToMessage.getSOAPPart();
		appendDocument(soapPart, appendToElement, srcDoc);
        return appendToElement;
	}
	
	/**
	 * 把Document添加到另一个Document的元素中。
	 * srcDoc是一个Document对象，要把这个对象添加到appendToDoc中的appendToElement里面。
	 * 返回appendToElement
	 * @param appendToDoc
	 * @param appendToElement
	 * @param srcDoc
	 * @return appendToElement
	 */
	public static Element appendDocument(Document appendToDoc, Element appendToElement, Document srcDoc) {
		Node srcNode = srcDoc.getDocumentElement();
        Node clone = appendToDoc.importNode(srcNode, true);
        appendToElement.appendChild(clone);
        return appendToElement;
	}
	
	/**
	 * 从SOAPMessage中获取SOAPBody，转换为Node返回
	 * @param soapMessage
	 * @return
	 * @throws SOAPException
	 */
	public static Node getBodyNodeFromSOAPMessage(SOAPMessage soapMessage) throws SOAPException {

		SOAPBody soapBody = soapMessage.getSOAPBody();

		return soapBody;
	}

	/**
	 * 解析SOAPMessage，转换SOAPBody中的内容为字符串返回
	 * @param soapMessage
	 * @return
	 * @throws SOAPException
	 * @throws TransformerException
	 */
	public static String getBodyXmlFromSOAPMessage(SOAPMessage soapMessage)
			throws SOAPException, TransformerException {
		String xmlStr = null;

		Node node = getBodyNodeFromSOAPMessage(soapMessage);
		xmlStr = nodeToXml(node);
		logger.debug("getBodyXmlFromSOAPMessage.\n" + xmlStr);
		
		return xmlStr;
	}
	
	/**
	 * 获取节点的第一个子元素（Element）
	 * @param node
	 * @return
	 */
	public static Element getFirstChildElement(Node node) {
		Element e = null;
		if(node==null) {
			return null;
		}
		NodeList nodeList = node.getChildNodes();
		for(int i=0;i<nodeList.getLength();i++) {
			Node n = nodeList.item(i);
			if(n instanceof Element) {
				e = (Element)n;
			}
		}
		return e;
	}
	/**
	 * 解析SOAPMessage，获取soapBodyRoot的子节点
	 * @param soapMessage
	 * @param soapBodyRoot
	 * @return
	 * @throws SOAPException
	 */
	public static Node getChildNodeFromSOAPMessage(SOAPMessage soapMessage, QName soapBodyRoot)
			throws SOAPException {

		Element rootElement = getFirstChildElement(soapMessage.getSOAPBody());
		if(rootElement==null) {
//			throw new RuntimeException("illegel soap message. soap body is empty.");
			return null;
		}
		if(! rootElement.getLocalName().equals(soapBodyRoot.getLocalPart())) {
			throw new RuntimeException("illegel soap message. "
					+ soapBodyRoot.getLocalPart() + " is expected, but got " + rootElement.getLocalName());
		}

		Element childElement = getFirstChildElement(rootElement);
		if(childElement==null) {
			return null;
		}
		
		return childElement;
	}

	/**
	 * 解析SOAPMessage，转换soapBodyRoot的子节点为字符串返回
	 * @param soapMessage
	 * @param soapBodyRoot
	 * @return
	 * @throws SOAPException
	 * @throws TransformerException
	 */
	@SuppressWarnings("unchecked")
	public static String getChildXmlFromSOAPMessage(SOAPMessage soapMessage, QName soapBodyRoot)
			throws SOAPException, TransformerException {
		String xmlStr = null;
		
		Node childNode = getChildNodeFromSOAPMessage(soapMessage, soapBodyRoot);

		xmlStr = nodeToXml(childNode);
		logger.debug("getChildXmlFromSOAPMessage.\n" + xmlStr);
		return xmlStr;
	}
	

	/**
	 * 从SOAPMessage获取invoke节点的子节点，返回节点名作为接口名
	 * @param soapMessage
	 * @return
	 * @throws SOAPException
	 * @throws TransformerException
	 */
	public static String getIfNameFromSOAPMessage(SOAPMessage soapMessage)
			throws SOAPException, TransformerException {
		Node invokeNode = getInvokeNode(soapMessage);
		String ifName = invokeNode.getLocalName();
		return ifName;
	}

//	private Node findRootElement(Document doc) {
//		Node n = null;
//		Element e = null;
//		
//		// 获取doc根节点（invoke）
//		e = doc.getDocumentElement();
//		if(! e.hasChildNodes()) {
//			logger.debug("! e.hasChildNodes");
//			throw new RuntimeException("empty document");
//		}
//		
//		// 获取invoke节点的子节点
//		n = e.getFirstChild();
//		if(n==null) {
//			throw new RuntimeException("child node for doc root");
//		}
//		String name = n.getLocalName();
//		logger.debug("node name: " + name);
//		
//		return n;
//	}
	
	
	/**
	 * Node转换为字符串
	 * @param node
	 * @return
	 * @throws TransformerException
	 */
	public static String nodeToXml (Node node) throws TransformerException {
		   StringWriter sw = new StringWriter();
		   TransformerFactory tf = TransformerFactory.newInstance();
		   Transformer transformer = tf.newTransformer();
		   transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		   transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		   transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		   transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		   transformer.transform(new DOMSource(node), new StreamResult(sw));
		   return sw.toString();
	}

	/**
	 * 字符串转换为Document
	 * @param xmlStr
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document xmlToDocument (String xmlStr) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // 忽略xmlns
        dbf.setNamespaceAware(false);
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream is = new ByteArrayInputStream(xmlStr.getBytes());
        Document doc = db.parse(is);
        
        return doc;
	}

}

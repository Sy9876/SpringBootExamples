package cn.sy.ws.client;

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.interceptor.StaxOutEndingInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.message.Message;

import cn.sy.ws.interceptor.MySoapInInterceptor;

public class MyWsClient {
	/*
	public static void invoke2() {
		try {
//			String addr = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx";
			String addr = "http://localhost:8089/mockWeatherWSSoap";
			System.out.println("start invoke");
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(WsSEI.class);
			factory.setAddress(addr);
		    WsSEI sv = (WsSEI) factory.create();
	
		    System.out.println("got sv");
//		    List<String> result = sv.getWeatherbyCityName("北京");
		    
//		    GetSupportCityStringSoapIn in = new GetSupportCityStringSoapIn();
//		    in.setTheRegionCode("311101");
//		    GetSupportCityStringSoapOut result = sv.getSupportCityString(in);
		    
//		    GetSupportCityStringSoapOut result = sv.getSupportCityString("311101");
		    
//		    List<String> result = sv.getSupportCityString("311101");
		    
		    GetSupportCityStringResult result = sv.getSupportCityString("311101");
		    
		    System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	public static void invoke() {
		try {
			
			System.out.println("start invoke");
			
			QName serviceName = new QName("http://WebXml.com.cn/", "WeatherWS");
			QName portName = new QName("http://WebXml.com.cn/", "WeatherWSSoap");
			String addr = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx";

			// 如果SEI中指定了wsdlLocation，这里就不需要传入wsdl地址
//			Service sv = Service.create(new URL(addr + "?wsdl"), serviceName);
			Service sv = Service.create(serviceName);
			sv.addPort(portName, SOAPBinding.SOAP12HTTP_BINDING, addr);
			
			WsSEI proxy = sv.getPort(WsSEI.class);
			
		    System.out.println("got sv");
//		    List<String> result = sv.getWeatherbyCityName("北京");
		    
		    Client client = ClientProxy.getClient(proxy);
		    Interceptor interceptor = null;
		    interceptor = new LoggingOutInterceptor();
//		    interceptor = new MySoapInInterceptor();
//		    String sh = null;
//		    interceptor = new StaxOutEndingInterceptor(sh);
		    client.getOutInterceptors().add(interceptor);
		    
		    
		    GetSupportCityStringSoapIn in = new GetSupportCityStringSoapIn();
//		    in.setTheRegionCode("311101");
//		    GetSupportCityStringSoapOut result = sv.getSupportCityString(in);
		    
//		    GetSupportCityStringSoapOut result = proxy.getSupportCityString("311101");
		    
//		    List<String> result = proxy.getSupportCityString("311101");
			
		    GetSupportCityStringResult result = proxy.getSupportCityString("311101");
		    if(result!=null) {
		    	for(String s : result.getString()) {
		    		System.out.println(s);
		    	}
		    }
		    System.out.println("invoke end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

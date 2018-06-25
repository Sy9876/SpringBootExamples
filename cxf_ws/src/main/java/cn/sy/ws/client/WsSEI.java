package cn.sy.ws.client;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.ResponseWrapper;

@WebService(name="WeatherWSSoap", 
	targetNamespace="http://WebXml.com.cn/",
	wsdlLocation="http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl"
	)
public interface WsSEI {

	@WebMethod(action="http://WebXml.com.cn/getWeatherbyCityName",
			operationName="getWeatherbyCityName")
	public @WebResult(name="getWeatherbyCityNameResult") List<String> getWeatherbyCityName(@WebParam(name="theCityName") String theCityName);

	
	
	
	@WebMethod(action="http://WebXml.com.cn/getSupportCityString",
			operationName="getSupportCityString")
//	public @WebResult(name="GetSupportCityStringSoapOut") GetSupportCityStringSoapOut getSupportCityString(@WebParam(name="getSupportCityString") GetSupportCityStringSoapIn in);
//	public 
//		GetSupportCityStringSoapOut getSupportCityString(
//				@WebParam(name="theRegionCode", targetNamespace="http://WebXml.com.cn/") String theRegionCode);

//	@ResponseWrapper(localName="GetSupportCityStringResponse", targetNamespace="http://WebXml.com.cn/",
//		className="cn.sy.ws.client.GetSupportCityStringResult")
	public 
	GetSupportCityStringResult getSupportCityString(
			@WebParam(name="theRegionCode") String theRegionCode);
}

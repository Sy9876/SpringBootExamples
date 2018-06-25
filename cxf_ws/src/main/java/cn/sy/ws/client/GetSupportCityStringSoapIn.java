package cn.sy.ws.client;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="getSupportCityString", namespace="http://WebXml.com.cn/")
public class GetSupportCityStringSoapIn implements Serializable {

	private static final long serialVersionUID = -144620931409856341L;
	
	@XmlElement(name = "theRegionCode")
	private String theRegionCode;

	
	
	public String getTheRegionCode() {
		return theRegionCode;
	}

	public void setTheRegionCode(String theRegionCode) {
		this.theRegionCode = theRegionCode;
	}
	
	
}

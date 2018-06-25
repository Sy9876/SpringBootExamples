package cn.sy.ws.client;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="GetSupportCityStringResponse", namespace="http://WebXml.com.cn/")
public class GetSupportCityStringSoapOut implements Serializable {

	private static final long serialVersionUID = -144620931409856341L;
	
	@XmlElementWrapper(name="GetSupportCityStringResult")
	@XmlElement(name = "string")
	private List<String> string;

	public List<String> getString() {
		return string;
	}

	public void setString(List<String> string) {
		this.string = string;
	}

}

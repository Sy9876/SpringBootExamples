package cn.sy.dto;

import java.util.Date;

public class DemoKafkaDto {

	private String v1;
	private String v2;
	private int v3;
	private Date v4;
	
	@Override
	public String toString() {
		return "v1=" + v1 + " v2=" + v2 + " v3=" + v3 + " v4=" + v4;
	}
	
	public String getV1() {
		return v1;
	}
	public void setV1(String v1) {
		this.v1 = v1;
	}
	public String getV2() {
		return v2;
	}
	public void setV2(String v2) {
		this.v2 = v2;
	}
	public int getV3() {
		return v3;
	}
	public void setV3(int v3) {
		this.v3 = v3;
	}
	public Date getV4() {
		return v4;
	}
	public void setV4(Date v4) {
		this.v4 = v4;
	}
	
	
}

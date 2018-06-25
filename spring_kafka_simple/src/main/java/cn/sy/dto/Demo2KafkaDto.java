package cn.sy.dto;

import java.util.Date;

public class Demo2KafkaDto {

	private String id;
	private String from;
	private String to;
	private Date ts;
	private String payloadStr;
	
	@Override
	public String toString() {
		return "id=" + id + " from=" + from + " to=" + to + " ts=" + ts + " payloadStr=" + payloadStr;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	public String getPayloadStr() {
		return payloadStr;
	}
	public void setPayloadStr(String payloadStr) {
		this.payloadStr = payloadStr;
	}

	
}

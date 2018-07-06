package cn.sy.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import cn.sy.kafka.MyKafkaListener;

@Component
@ManagedResource(objectName="bean:name=myOffsetMBean", description="consumer offset manage bean")
public class MyOffsetMBean {

	private static Logger logger = LoggerFactory.getLogger(MyOffsetMBean.class);
			
	private String topic;
	private int partition;
	private long offset;
	
	private static MyKafkaListener listener = null;
	
	public static void registerListener(MyKafkaListener listener) {
		MyOffsetMBean.listener = listener;
	}
	
	
	@ManagedOperation
	public void seekToOffset(long seekTo) {
		logger.info("seekToOffset seekTo: " + seekTo);
		if(listener != null) {
			listener.doJmxOperationSeedTo(seekTo);
		}
	}
	
	
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public int getPartition() {
		return partition;
	}
	public void setPartition(int partition) {
		this.partition = partition;
	}
	
	@ManagedAttribute()
	public long getOffset() {
		logger.info("getOffset");
		return offset;
	}
	@ManagedAttribute(description="setOffset")
	public void setOffset(long offset) {
		logger.info("getOffset to " + offset);
		this.offset = offset;
	}
	

	public void dontExposeMe() {  
        throw new RuntimeException();  
    } 
}

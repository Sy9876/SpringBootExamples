package cn.sy.kafka;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.sy.dto.Demo2KafkaDto;
import cn.sy.dto.DemoKafkaDto;
import cn.sy.jmx.MyOffsetMBean;

import org.springframework.kafka.support.KafkaHeaders;
@Component
@KafkaListener(topics = "myTopic", groupId="g1"
,containerFactory = "kafkaJsonListenerContainerFactory"
)
public class MyKafkaListener implements ConsumerSeekAware {
	private static Logger logger = LoggerFactory.getLogger(KafkaListener.class);

	private ConsumerSeekCallback consumerSeekCallback;
	
//	@Autowired
//	private KafkaTemplate<String, String> kafkaTemplate;
	
	
	/**
	 * 使用@KafkaListener(topics = "myTopic") 声明多个方法，只有一个能够接收信息（因为group相同），
	 * 但，如果方法参数类型不同，则根据消息的类型调用相应的方法。
	 * 例如
	 *  @KafkaListener(id = "multi", topics = "myTopic")
	 *  static class MultiListenerBean {
	 *  	@KafkaHandler
	 *  	public void listen(String foo) {
	 *  		...
	 *  	}
	 *  	@KafkaHandler
	 *  	public void listen(Integer bar) {
	 *  		...
	 *  	}
	 *  }
	 * 
	 * 使用@KafkaListener(topics = "myTopic", groupId="xx") 声明多个方法，
	 * 只要groupId不同，则都能够接收信息
	 * 
	 * 
	 */
	
//	@KafkaListener(topics = "myTopic", groupId="g1"
//			 ,containerFactory = "kafkaJsonListenerContainerFactory"
//			)

//	@KafkaListener(topics = "myTopic", groupId="g1")
	@KafkaHandler()
	public void listen3(String payload) throws Exception { 
	
		logger.info("listen3 myTopic payload: " + payload);

	}
    
//	@KafkaListener(topics = "myTopic", groupId="g1")
//	@KafkaHandler()
//	public void listen(Integer payload) throws Exception { 
//	
//		logger.info("listen4 myTopic payload: " + payload);
//
//	}
    

	@KafkaHandler()
	public void listen(DemoKafkaDto msg,
			@Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) throws Exception { 
	
		logger.info("listen myTopic DemoKafkaDto msg: " + msg.toString()
				+ " " + KafkaHeaders.RECEIVED_TIMESTAMP + ": " + ts);

	}

	@KafkaHandler()
	public void listen(@Payload Demo2KafkaDto msg,
			@Header(KafkaHeaders.OFFSET) long offsets,
			@Header(name=KafkaHeaders.RECEIVED_MESSAGE_KEY, required=false) Integer key,
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
			@Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
			@Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts,
			@Header("PayLoadType") byte[] payLoadType,
			@Header("From") byte[] from,
			@Header("To") byte[] to
			) throws Exception { 
	
		logger.info("listen myTopic Demo2KafkaDto msg: " + msg.toString()
			+ "\n  " + KafkaHeaders.OFFSET + ": " + offsets
			+ "\n  " + KafkaHeaders.RECEIVED_MESSAGE_KEY + ": " + key
			+ "\n  " + KafkaHeaders.RECEIVED_PARTITION_ID + ": " + partition
			+ "\n  " + KafkaHeaders.RECEIVED_TOPIC + ": " + topic
			+ "\n  " + KafkaHeaders.RECEIVED_TIMESTAMP + ": " + ts
			+ "\n  " + "PayLoadType" + ": " + Utils.utf8(payLoadType)
			+ "\n  " + "From" + ": " + Utils.utf8(from)
			+ "\n  " + "To" + ": " + Utils.utf8(to)
			);

	}


	@KafkaHandler()
	public void listen(@Payload List<Demo2KafkaDto> msgs,
			@Header(KafkaHeaders.OFFSET) long offsets,
			@Header(name=KafkaHeaders.RECEIVED_MESSAGE_KEY, required=false) Integer key,
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
			@Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
			@Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts,
			@Header("PayLoadType") byte[] payLoadType,
			@Header("From") byte[] from,
			@Header("To") byte[] to
			) throws Exception { 
	
		StringBuffer sb = new StringBuffer();
		for(Demo2KafkaDto dto : msgs) {
			sb.append(dto.toString());
			sb.append("\n");
		}
		logger.info("listen myTopic Demo2KafkaDto msg: " + sb.toString()
			+ "\n  " + KafkaHeaders.OFFSET + ": " + offsets
			+ "\n  " + KafkaHeaders.RECEIVED_MESSAGE_KEY + ": " + key
			+ "\n  " + KafkaHeaders.RECEIVED_PARTITION_ID + ": " + partition
			+ "\n  " + KafkaHeaders.RECEIVED_TOPIC + ": " + topic
			+ "\n  " + KafkaHeaders.RECEIVED_TIMESTAMP + ": " + ts
			+ "\n  " + "PayLoadType" + ": " + Utils.utf8(payLoadType)
			+ "\n  " + "From" + ": " + Utils.utf8(from)
			+ "\n  " + "To" + ": " + Utils.utf8(to)
			);

	}
	
//	@KafkaListener(topics = "myTopic", groupId="g1"
//			 ,containerFactory = "kafkaJsonListenerContainerFactory"
//			)
//	@KafkaHandler()
	public void listen(ConsumerRecord<String, String> cr) throws Exception { 
	
		logger.info("listen myTopic receive: " + cr.toString());

	}
    
//	@KafkaListener(topics = "myTopic", groupId="g2")
//	public void listen2(ConsumerRecord<String, String> cr) throws Exception { 
//	
//		logger.info("listen2 myTopic receive: " + cr.toString());
//
//	}

	

	@Override
	public void registerSeekCallback(ConsumerSeekCallback callback) {
		logger.info("on registerSeekCallback. " + callback);
		
		// save callback
		consumerSeekCallback=callback;
		
	}

	@Override
	public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
		logger.info("on onPartitionsAssigned. " + assignments);

		String topic = "myTopic";
		int partition = 0;
		long offset = 0;
		
		TopicPartition tp = new TopicPartition(topic, partition);
		offset = assignments.get(tp);
		if(offset-1>0) {
			logger.info("on onPartitionsAssigned. seek to " + topic + " " + partition + " " + (offset - 1));
			callback.seek(topic, partition, offset - 1);
		}
		
	}

	@Override
	public void onIdleContainer(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
		logger.info("on onIdleContainer. " + assignments);

		
	}
    
	
	@PostConstruct
	public void registerToJmxBean() {
		logger.info("registerToJmxBean. ");
		MyOffsetMBean.registerListener(this);
	}
	
	public void doJmxOperationSeedTo(long seekTo) {
		logger.info("doJmxOperationSeedTo. seekTo=" + seekTo);
		String topic = "myTopic";
		int partition = 0;
		long offset = seekTo;
		logger.info("on doJmxOperationSeedTo. seek to " + topic + " " + partition + " " + offset);
		consumerSeekCallback.seek(topic, partition, offset);
	}
	
	
}

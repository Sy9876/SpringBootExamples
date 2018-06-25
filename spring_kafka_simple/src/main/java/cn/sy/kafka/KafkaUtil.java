package cn.sy.kafka;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.sy.dto.Demo2KafkaDto;
import cn.sy.dto.DemoKafkaDto;


public class KafkaUtil {
	private static Logger logger = LoggerFactory.getLogger(KafkaUtil.class);
		
//	@Autowired
//	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private KafkaTemplate<String, Object> jsonKafkaTemplate;
	

	public void send(String topic, Object o) {
		
    	RecordHeaders headers = new RecordHeaders();
    	String payLoadType = o.getClass().getName();
        
        headers.add("PayLoadType", Utils.utf8(payLoadType));
    	headers.add("From", Utils.utf8("B"));
    	headers.add("To", Utils.utf8("S"));
    	
   	
    	// String topic, Integer partition, K key, V value,  Iterable<Header> headers
    	ProducerRecord<String, Object> record = new ProducerRecord<String, Object>(
    			topic, null, null, o, headers);
    	
		jsonKafkaTemplate.send(record);
	}
}

package cn.sy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import cn.sy.kafka.KafkaUtil;

@RestController
public class KafkaMsgController {
	private static Logger logger = LoggerFactory.getLogger(KafkaMsgController.class);
	
	@Autowired
	private KafkaUtil kafkaUtil;
	
    
    @RequestMapping("/public/sendKafkaMsgStr.do")
    public void sendKafkaMsgStr() {

    	kafkaUtil.send("myTopic", "foo1");
    	
    	logger.info("sendKafkaMsgStr.do end");
    }
    
    @RequestMapping("/public/sendKafkaMsgInt.do")
    public void sendInt(int count) {

    	kafkaUtil.send("myTopic", count);
    	
    	logger.info("sendKafkaMsgInt.do end");
    }
    
    
    @RequestMapping("/public/sendKafkaMsg.do")
    public void sendKafkaMsg() {

    	DemoKafkaDto dto = new DemoKafkaDto();
    	dto.setV1(UUID.randomUUID().toString());
    	dto.setV2(null);
    	dto.setV3(0);
    	dto.setV4(new Date());
    	
    	kafkaUtil.send("myTopic", dto);
    	
    	logger.info("sendKafkaMsg.do end");
    }
    
    
    @RequestMapping("/public/sendKafkaMsg2.do")
    public void sendKafkaMsg2() {

    	Demo2KafkaDto dto = new Demo2KafkaDto();
    	dto.setId(UUID.randomUUID().toString());
    	dto.setFrom("from");
    	dto.setTo("to");
    	dto.setTs(new Date());
    	dto.setPayloadStr("sendKafkaMsg2.do");
    	
//    	MyKafkaMessage<Demo2KafkaDto> msg = new MyKafkaMessage<Demo2KafkaDto>();
//    	msg.setPayload(dto);
    	    	
    	logger.info("sendKafkaMsg2.do use kafkaUtil send dto: " + dto);
    	kafkaUtil.send("myTopic", dto);

    }

    
    @RequestMapping("/public/sendDto2List.do")
    public void sendDto2List() {

    	List<Demo2KafkaDto> list = new ArrayList<>();
    	
    	Demo2KafkaDto dto = new Demo2KafkaDto();
    	dto.setId(UUID.randomUUID().toString());
    	dto.setFrom("from");
    	dto.setTo("to");
    	dto.setTs(new Date());
    	dto.setPayloadStr("sendDto2List.do");
    	list.add(dto);
    	
    	dto = new Demo2KafkaDto();
    	dto.setId(UUID.randomUUID().toString());
    	dto.setFrom("from");
    	dto.setTo("to");
    	dto.setTs(new Date());
    	dto.setPayloadStr("222");
    	list.add(dto);
    	
    	
//    	MyKafkaMessage<Demo2KafkaDto> msg = new MyKafkaMessage<Demo2KafkaDto>();
//    	msg.setPayload(dto);
    	    	
    	logger.info("sendDto2List.do use kafkaUtil send dto: " + list);
    	kafkaUtil.send("myTopic", list);

    }


}

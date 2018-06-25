package cn.sy.controller;

import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.sy.dto.Demo2KafkaDto;
import cn.sy.dto.DemoKafkaDto;
import cn.sy.kafka.KafkaUtil;

@RestController
public class KafkaMsgController {
	private static Logger logger = LoggerFactory.getLogger(KafkaMsgController.class);
	
	@Autowired
	private KafkaUtil kafkaUtil;
	
	// 使用@PostConstruct在stringRedisTemplate被注入之后执行redisRoutingMap的初始化
	@PostConstruct
	public void init() {
		logger.info("PostConstruct. init ");
	}

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
    	
    	logger.info("sendKafkaMsg2.do use kafkaUtil send dto: " + dto);
    	kafkaUtil.send("myTopic", dto);

    }

}

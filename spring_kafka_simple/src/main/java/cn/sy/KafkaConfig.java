package cn.sy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import cn.sy.kafka.KafkaUtil;
import cn.sy.kafka.MyStringJsonMessageConverter;

@Configuration
@EnableKafka
public class KafkaConfig {

	
//	@Bean
//	public Map<String, Object> consumerConfigs() {
//		Map<String, Object> props = new HashMap<>();
//		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafka.getBrokersAsString());
//
//		return props;
//	}
	
//	@Bean
//	public ConsumerFactory<Integer, String> consumerFactory() {
//		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
//	}
//	
//	
	@Autowired
	private ConsumerFactory<String, String> consumerFactory;
	
	@Bean
	public KafkaListenerContainerFactory<?> kafkaJsonListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
//		factory.setMessageConverter(new StringJsonMessageConverter());
		factory.setMessageConverter(new MyStringJsonMessageConverter());
		
		
		return factory;
	}
//	
//	
//	@Autowired
//	private ProducerFactory<String, ?> producerFactory;
//	
//	@Bean
//	public KafkaTemplate<String, ?> kafkaTemplate() {
//		KafkaTemplate<String, ?> template = new KafkaTemplate<>(producerFactory);
//		template.setMessageConverter(new StringJsonMessageConverter());
//		return template;
//	}
	
	@Bean
	public KafkaUtil kafkaUtil() {
		return new KafkaUtil();
	}
	
}

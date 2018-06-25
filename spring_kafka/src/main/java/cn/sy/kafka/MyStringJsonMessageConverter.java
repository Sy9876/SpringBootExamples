package cn.sy.kafka;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.kafka.support.converter.ConversionException;
import org.springframework.kafka.support.converter.MessagingMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.utils.Utils;

public class MyStringJsonMessageConverter extends MessagingMessageConverter {

	private final ObjectMapper objectMapper;

	public MyStringJsonMessageConverter() {
		this(new ObjectMapper());
		this.objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
		this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public MyStringJsonMessageConverter(ObjectMapper objectMapper) {
		Assert.notNull(objectMapper, "'objectMapper' must not be null.");
		this.objectMapper = objectMapper;
	}

	@Override
	protected Object convertPayload(Message<?> message) {
		try {
			return this.objectMapper.writeValueAsString(message.getPayload());
		}
		catch (JsonProcessingException e) {
			throw new ConversionException("Failed to convert to JSON", e);
		}
	}


	@Override
	protected Object extractAndConvertValue(ConsumerRecord<?, ?> record, Type type) {
		Object value = record.value();
		if (record.value() == null) {
			return KafkaNull.INSTANCE;
		}

		Iterable<Header> headers = record.headers().headers("PayLoadType");
		Iterator<Header> it = headers.iterator();
		Header header = null;
		String typeStr = null;
		Class<? extends Object> clazz = null;
		while(it.hasNext()) {
			header = it.next();
			typeStr = Utils.utf8(header.value());
			try {
//				clazz = Class.forName(typeStr);
				clazz = ClassUtils.forName(typeStr, null);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
//				throw new RuntimeException(e);
			}
		}
		JavaType javaType = TypeFactory.defaultInstance().constructType(type);
		if(clazz != null) {
//			Type type2 = clazz.getGenericSuperclass();
			Type type2 = clazz;
			javaType = TypeFactory.defaultInstance().constructType(type2);
		}
		
		if (value instanceof String) {
			try {
				if(javaType.isJavaLangObject()) {
					// javaType is java.lang.Object. return String
					return value;
				}
				else {
					return this.objectMapper.readValue((String) value, javaType);
				}
			}
			catch (IOException e) {
				throw new ConversionException("Failed to convert from JSON", e);
			}
		}
		else if (value instanceof byte[]) {
			try {
				return this.objectMapper.readValue((byte[]) value, javaType);
			}
			catch (IOException e) {
				throw new ConversionException("Failed to convert from JSON", e);
			}
		}
		else {
			throw new IllegalStateException("Only String or byte[] supported");
		}
	}

}

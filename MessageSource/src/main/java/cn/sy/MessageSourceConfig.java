package cn.sy;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {

	private static String BASENAME = "message_task";
	
	@Bean
	public MessageSource messageSource() {
		
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		
		messageSource.addBasenames(BASENAME);
		messageSource.setUseCodeAsDefaultMessage(true);

		return messageSource;
	}
}

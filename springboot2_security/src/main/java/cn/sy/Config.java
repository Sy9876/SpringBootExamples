package cn.sy;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * 需要@EnableSpringHttpSession来启用session，并需要声明一个sessionRepository（@Bean MapSessionRepository）
 * 
 * 用HttpSessionIdResolver来禁用cookie，启用x-auth-token头。
 * 
 * 
 * @author Administrator
 *
 */
@Configuration
@EnableSpringHttpSession
public class Config {
	@Bean
	public HttpSessionIdResolver httpSessionIdResolver() {
		return HeaderHttpSessionIdResolver.xAuthToken(); 
	}

	@Bean
	public MapSessionRepository sessionRepository() {
		Map<String, Session> sessions = new HashMap<String, Session>();
		MapSessionRepository sessionRepository = new MapSessionRepository(sessions);
		return sessionRepository;
	}
}

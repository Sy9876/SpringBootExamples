package cn.sy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@Configuration
//@EnableJdbcHttpSession
@EnableRedisHttpSession
public class SessionConfig {
	// spring.session.store-type=jdbc in application.properties not work.

	/**
	 * use X-Auth-Token for REST
	 * @return
	 */
	// spring-session 1.3没有这个方法。默认就是X-Auth-Token，不需要设置。（不确定）
	// 2.x才有这个方法。但与spring-data-redis 1.8.9不兼容
//	@Bean
//	public HttpSessionIdResolver httpSessionIdResolver() {
//		return HeaderHttpSessionIdResolver.xAuthToken(); 
//	}
}

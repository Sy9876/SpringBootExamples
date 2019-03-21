package cn.sy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.util.StringUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	
	// UserDetailsService 可以由springboot自动注入。
	// 是InMemoryUserDetailsManager实例，从application.properties中加载账号.
	// 但是这样就只能用servlet.login进行登录，不能使用AuthenticationManager.authenticate。
	// 因为
	//  UserDetailsServiceAutoConfiguration
	//  @ConditionalOnMissingBean({ AuthenticationManager.class, AuthenticationProvider.class, UserDetailsService.class })
	
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		AuthenticationProvider provider = new AuthenticationProvider();
//	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		UserDetails userDetails = User.withDefaultPasswordEncoder()
				.username("admin")
				.password("admin123")
				.roles("USER", "ADMIN")
				.build();
		
		manager.createUser(userDetails);
		return manager;
	}
	

	/**
	 * 禁用csrf
	 * 
	 * 启用session
	 * 
	 */
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		// disable csrf for restapi ?
        http
        	.csrf()
//        	.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//        	.csrfTokenRepository(new HttpSessionCsrfTokenRepository())
        	
        	.disable()
//        	.and()

        	// 或通过配置 security.sessions=always ?
        	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
        	.and()
        	
        	.authorizeRequests()
	        .antMatchers("/void.do", "/login.do", "/actuator/**").permitAll()

//	        .antMatchers("/**").permitAll()
	        .anyRequest().authenticated()
                ;
    }
	
}

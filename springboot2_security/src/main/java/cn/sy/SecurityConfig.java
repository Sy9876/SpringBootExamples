package cn.sy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cn.sy.dao.UserDao;
import cn.sy.domain.MyUserDetails;

@Configuration
@EnableWebSecurity
@PropertySource("security.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	private UserDao userDetailsService;
	
	@Value("${secure.key}")
	private String secureKey;
	
	@Value("${secure.ad.domain}")
	private String secureAdDomain;
	
	@Value("${secure.ad.url}")
	private String secureAdUrl;
	
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService) {
		logger.info("daoAuthenticationProvider");
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
	
	@Bean
	public ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
		logger.info("activeDirectoryLdapAuthenticationProvider");
		

		ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(secureAdDomain, secureAdUrl);

		provider.setUserDetailsContextMapper(new UserDetailsContextMapper() {

			/**
			 * 
			 */
			@Override
			public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
					Collection<? extends GrantedAuthority> authorities) {
				MyUserDetails userDetails = null;
//				userDetails = new MyUserDetails();
//				userDetails.setName(username);
				try {
					userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(username);
					userDetails.setMenus(userDetailsService.findMenusByName(username));
				} catch (Exception e) {
					throw e;
				}
				return userDetails;
			}

			/**
			 * we do not use this.
			 */
			@Override
			public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
			}
			
		});
		return provider;
	}
	
	@Bean
	public ProviderManager providerManager(DaoAuthenticationProvider daoAuthenticationProvider
			,ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider
			) {
		logger.info("providerManager");
		List<AuthenticationProvider> providers = new ArrayList<AuthenticationProvider>();
		providers.add(daoAuthenticationProvider);
		providers.add(activeDirectoryLdapAuthenticationProvider);
		return new ProviderManager(providers);
	}
	
	/*
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		logger.info("secureKey=" + secureKey);
//		auth
//		.jdbcAuthentication()
//			.dataSource(dataSource)
//			.withDefaultSchema()
//			.withUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER"));
//		auth
//		.userDetailsService(userDetailsService);
	
			
	}
	*/
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		// disable csrf for restapi ?
        http
        	.csrf()
//        	.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        	.disable()	
//        	.and()

        	.authorizeRequests()
	        .antMatchers("/void.do", "/login.do").permitAll()
//	        .antMatchers("/count.do").authenticated()
//	        .antMatchers("/user.do").hasRole("USER")
//	        .antMatchers("/insert.do").hasRole("ADMIN")
//	        .antMatchers("/menus.do").hasRole("ADMIN")
	        
	        // hasRole match to ROLE_ + str
	        .antMatchers("/count.do").hasRole("count")
	        .antMatchers("/user.do").hasRole("user")
	        .antMatchers("/insert.do").hasRole("insert")
	        .antMatchers("/menus.do").hasRole("menus")
	        
//	        .antMatchers("/**").permitAll()
	        .anyRequest().authenticated()
//                .and().formLogin().loginPage("/login").failureUrl("/login?error").permitAll().and()
//                .logout()
//                .permitAll()
                ;
    }
	
}

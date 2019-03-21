package cn.sy.controller;

import java.security.Key;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@RestController
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);


//	@Autowired
//	AuthenticationManager authenticationManager;
	private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
    @RequestMapping("/login.do")
    public String login(
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="password", required=true) String password,
			HttpServletRequest request
			) throws Exception {
    	
    	UserDetails principal = null;
    	String jws = null;
		
    	logger.info("login.do start. name=" + name + " password=" + password);
    	try {

    		String[] authMethods = {"AuthenticationManager", "Servlet"};
    		String authMethod = authMethods[1];
    		logger.info("login.do use " + authMethod);
    		
    		try {
//    			if(authMethod.equals("AuthenticationManager")) {
//    	    		Authentication request = new UsernamePasswordAuthenticationToken(name, password);
//    	    		logger.info("login.do", request);
//    	    		Authentication result = authenticationManager.authenticate(request);
//    	    		logger.info("login.do", result);
//    	    		SecurityContextHolder.getContext().setAuthentication(result);
//    			}

    			if(authMethod.equals("Servlet")) {
//	    			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
//	    					.getRequestAttributes()).getRequest();
	    			request.login(name, password);
	    			
	    			jws = Jwts.builder().setSubject(name).signWith(key).compact();
	    			logger.info("login.do  request.login done  jws：" + jws);
    			}
    			
    			logger.info("login.do  request.login done");
    			
    			principal = getPrincipal();
    			
			} catch(AuthenticationException e) {
				System.out.println("Authentication failed: " + e.getMessage());
				throw e;
    		}

    		logger.info("login.do");
		} catch (Exception e) {
			logger.error("login.do", e);
			throw e;
		}
    	logger.info("login.do end. principal: " + principal);
    	return jws;
    }
    
    @RequestMapping("/logout.do")
    public String logout() throws ServletException {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		request.logout();
		logger.info("logout.do  request.logout done");
    	return "OK";
    }

    // curl -i -X POST -H 'Content-type: application/json' -d '{"name":"sy", "password":"shenyue"}' 'http://localhost:8080/login.do?name=admin&password=admin123'
    // 
    // jws=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiJ9.BG3EClUo-ZpGUBPzEuXd_RuTffGzLnBVOos28ZiZYv4
    // curl -i -X POST -H 'Content-type: application/json' -H "X-Auth-Token: $XAT" 'http://localhost:8080/whoami.do?jws='$jws
    @RequestMapping("/whoami.do")
    public String whoami(String jws) throws Exception {
    	
//    	UserDetails principal = getPrincipal();
    	String user = null;
    	
//    	String jws = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiJ9.qpiLzBZolx7HSvMqHwaKUIs-w8xvIMM94gG5xB-DFHY";
    	
    	try {
    		Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(key).parseClaimsJws(jws);
    		user = jwsClaims.getBody().getSubject();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
//    	logger.info("whoami.do end. principal: " + principal);
    	logger.info("whoami.do end. user: " + user);
    	
    	return user;
    }
    
    @RequestMapping("/void.do")
    public void empty() {

    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	logger.info("void.do principal: " + authentication.isAuthenticated());
    	logger.info("void.do user: " + authentication.getName());
    	//    	System.out.println("void.do start.");
    	logger.info("void.do end");
    }
    
    
    private UserDetails getPrincipal() {
    	UserDetails principal = null;
    	try {
    		Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		if(o instanceof UserDetails) {
    			principal=(UserDetails)o;
    		}
    		logger.info("whoami.do", principal);
		} catch (Exception e) {
			logger.error("whoami.do", e);
			throw e;
		}
    	
    	return principal;
    }
    
    @ExceptionHandler(value=Exception.class)
    @ResponseStatus
    public ErrorRespBody handleError(Exception ex) {
    	return new ErrorRespBody(1001, "ERROR: " + ex.getMessage());
    }
    
    private class ErrorRespBody {
    	private int code;
    	private String message;
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public ErrorRespBody() {
			
		}
		public ErrorRespBody(int code, String message) {
			super();
			this.code = code;
			this.message = message;
		}
    	
    	
    }
}

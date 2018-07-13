package cn.sy.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.sy.domain.MyUserDetails;
import cn.sy.domain.User;


@RestController
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);


	@Autowired
	AuthenticationManager auth;
	
    @RequestMapping("/login.do")
    public UserDetails login(
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="password", required=true) String password) throws Exception {
    	
    	UserDetails principal = null;

    	logger.info("login.do start. name=" + name + " password=" + password);
    	try {

    		String[] authMethods = {"AuthenticationManager", "Servlet"};
    		String authMethod = authMethods[0];
    		
    		logger.info("login.do use " + authMethod);
    		
    		try {
    			if(authMethod.equals("AuthenticationManager")) {
    	    		Authentication request = new UsernamePasswordAuthenticationToken(name, password);
    	    		logger.info("login.do", request);
    	    		Authentication result = auth.authenticate(request);
    	    		logger.info("login.do", result);
    	    		SecurityContextHolder.getContext().setAuthentication(result);
    			}

    			if(authMethod.equals("Servlet")) {
	    			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
	    					.getRequestAttributes()).getRequest();
	    			request.login(name, password);
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
    	return principal;
    }
    
    @RequestMapping("/logout.do")
    public String logout() throws ServletException {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		request.logout();
		logger.info("logout.do  request.logout done");
    	return "OK";
    }

    @RequestMapping("/whoami.do")
    public UserDetails whoami() throws Exception {
    	
    	UserDetails principal = getPrincipal();
    	
    	logger.info("whoami.do end. principal: " + principal);
    	
    	return principal;
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

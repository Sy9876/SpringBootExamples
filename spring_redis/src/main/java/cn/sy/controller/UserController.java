package cn.sy.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
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

import cn.sy.dao.UserDao;
import cn.sy.domain.MyUserDetails;
import cn.sy.domain.User;


@RestController
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	AuthenticationManager auth;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Resource(name="stringRedisTemplate")
	private HashOperations<String, String, String> hashOps;
	
	// 实现了ConcurrentMap<K,V>，线程安全。每次get都从redis查询，性能有问题？
	DefaultRedisMap<String, String> redisRoutingMap;
	
//	private RedisOperations<String, String> redisOperations;
	
	// 使用@PostConstruct在stringRedisTemplate被注入之后执行redisRoutingMap的初始化
	@PostConstruct
	public void init() {
		logger.info("PostConstruct. init redisRoutingMap");
		String k = "shardRule:shop";
		redisRoutingMap = new DefaultRedisMap<>(k, stringRedisTemplate);
	}
	
    @RequestMapping("/login.do")
    public User login(
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="password", required=true) String password) {
    	
    	User user = null;

    	logger.info("login.do start. name=" + name + " password=" + password);
    	try {
    		user=userDao.findByName(name);
    		
    		try {
	    		Authentication request = new UsernamePasswordAuthenticationToken(name, password);
	    		logger.info("login.do", request);
	    		Authentication result = auth.authenticate(request);
	    		logger.info("login.do", result);
	    		SecurityContextHolder.getContext().setAuthentication(result);

			} catch(AuthenticationException e) {
				System.out.println("Authentication failed: " + e.getMessage());
				throw e;
    		}

    		Authentication a = SecurityContextHolder.getContext().getAuthentication();
    		System.out.println("Successfully authenticated. Security context contains: " +
    		a);

    		MyUserDetails d = (MyUserDetails)a.getDetails();
    		System.out.println("Successfully authenticated. getDetails: " + d);

    		MyUserDetails p = (MyUserDetails)a.getPrincipal();
    		System.out.println("Successfully authenticated. getPrincipal: " + p);
    		
    		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		String username = null;
    		if (principal instanceof UserDetails) {
    			username = ((UserDetails)principal).getUsername();
    			
    		} else {
    			username = principal.toString();
    		}
    		System.out.println("Successfully authenticated. username: " + username);

    		logger.info("login.do", user);
		} catch (Exception e) {
			logger.error("login.do", e);
			throw e;
		}
    	logger.info("login.do end");
    	return user;
    }
    

	@RequestMapping("/user.do")
    public User greeting(
			@RequestParam(value="name", required=true) String name) {
    	
    	User user = null;
    	
//    	System.out.println("user start. name=" + name);
    	logger.info("user.do start. name=" + name);
    	try {
    		user=userDao.findByName(name);
//    		System.out.println(user);
    		logger.info("user.do", user);
		} catch (Exception e) {
//			e.printStackTrace();
			logger.error("user.do", e);
			throw e;
		}
//    	System.out.println("user end");
    	logger.info("user.do end");
    	return user;
    }

    
	// set，get操作
    private void redisValueTest() {
    	String k = "myKey";
    	String v = null;
    	v = stringRedisTemplate.opsForValue().get(k);
    	if(v!=null) {
    		v=v+".";
    	}
    	else {
    		v=".";
    	}
    	stringRedisTemplate.opsForValue().set(k, v);
    }
    
    // 使用spring data redis 提供的帮助类DefaultRedisMap实现hash操作
    private void redisHashTest() {
    	
    	String kk = null;
    	String v = null;

    	for(int i=0;i<10;i++) {
    		kk = "shop_" + i;
    		v = "" + i%10;
    		redisRoutingMap.put(kk, v);
    	}
    	

    }
    
	// dump hash
    private void redisHashTest2() {

    	String kk = null;
    	String v = null;

    	logger.info("redisHashTest2. redisRoutingMap.size=" + redisRoutingMap.size());
    	Set<String> ks = redisRoutingMap.keySet();
    	Iterator<String> i = ks.iterator();
    	while (i.hasNext()) {
    		kk = i.next();
    		logger.info("redisHashTest2. kk=" + kk + "  v=" + redisRoutingMap.get(kk));
    	}

    }
    
    @RequestMapping("/public/void.do")
    public void empty() {

    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	logger.info("void.do principal: " + authentication.isAuthenticated());
    	logger.info("void.do user: " + authentication.getName());
    	//    	System.out.println("void.do start.");
    	
//    	redisValueTest();
    	redisHashTest();
    	
    	logger.info("void.do end");
    }
    
    @RequestMapping("/public/void2.do")
    public void empty2() {


//    	redisValueTest();
//    	redisHashTest();
    	redisHashTest2();
    	
    	logger.info("void2.do end");
    }
    
    @RequestMapping("/count.do")
    public int count() {
    	int cnt=0;
//    	System.out.println("count.do start.");
    	logger.info("count.do start");
    	try {
    		cnt=userDao.count();
//    		System.out.println(cnt);
    		logger.info("count.do ", cnt);
		} catch (Exception e) {
//			e.printStackTrace();
			logger.error("count.do ", e);
		}
//    	System.out.println("count.do end");
    	logger.info("count.do end");
    	return cnt;
    }

    @RequestMapping("/menus.do")
    public List<Map<String, String>> menus(@RequestParam(value="name", required=true) String name) {
    	List<Map<String, String>> menus = null;

    	logger.info("menus.do start");
    	try {
    		menus=userDao.findMenusByName(name);
//    		logger.info("menus.do ");
		} catch (Exception e) {

			logger.error("menus.do ", e);
		}

    	logger.info("menus.do end");
    	return menus;
    }

    @RequestMapping("/insert.do")
    public int insert() {
    	int cnt=0;
//    	System.out.println("insert.do start.");
    	logger.info("insert.do start");
    	try {
    		cnt=userDao.insert(new User("1", "admin", "1", "password"));
//    		System.out.println(cnt);
    		logger.info("insert.do ", cnt);
		} catch (Exception e) {
//			e.printStackTrace();
			logger.error("insert.do ", e);
		}
//    	System.out.println("insert end");
    	logger.info("insert.do end");
    	return cnt;
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

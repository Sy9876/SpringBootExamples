package cn.sy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.sy.dao.UserDao;
import cn.sy.domain.User;
import cn.sy.ws.client.MyWsClient;


@RestController
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDao userDao;

    @ResponseBody
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
		}
//    	System.out.println("user end");
    	logger.info("user.do end");
    	return user;
    }

    @ResponseBody
    @RequestMapping("/void.do")
    public void empty() {

    	MyWsClient.invoke();
    	
//    	System.out.println("void.do start.");
    	logger.info("void.do end");
    }
    
    @ResponseBody
    @RequestMapping("/count.do")
    public int count() {
    	int cnt=0;
//    	System.out.println("count.do start.");
    	logger.info("count.do end");
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

    
    @ResponseBody
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
}

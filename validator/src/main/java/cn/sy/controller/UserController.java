package cn.sy.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.sy.domain.User;


@RestController
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @ResponseBody
    @RequestMapping("/user.do")
    public User greeting(
    		@RequestParam(value="name", required=true) String name) {
    	
    	User user = null;

    	logger.info("user.do start. name=" + name);
    	try {
    		
    		logger.info("user.do", user);
		} catch (Exception e) {

			logger.error("user.do", e);
		}

    	logger.info("user.do end");
    	return user;
    }

    @ResponseBody
    @RequestMapping("/void.do")
    public void empty() {


    	logger.info("void.do end");
    }
        
    // curl -i --silent -X POST http://localhost:8080/insert.do --data "{"""id""":"""test3""","""name""":null,"""status""":1,"""password""":"""123456"""}"  -H "Content-Type: application/json" -H "Accept: application/json" -H "X-Requested-With: XMLHttpRequest"
    @ResponseBody
    @RequestMapping("/insert.do")
    public int insert(@Valid @RequestBody User user, BindingResult result) {
    	int cnt=0;

    	logger.info("insert.do start");
    	try {
//    		User user = new User("1", "admin", "1", "password");

    		if (result.hasErrors()){
                List<ObjectError> errorList = result.getAllErrors();
                for(ObjectError error : errorList){
                	logger.info(error.toString());
                }
            }

    		logger.info("insert.do ", cnt);
		} catch (Exception e) {

			logger.error("insert.do ", e);
		}

    	logger.info("insert.do end");
    	return cnt;
    }
}

package cn.sy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.sy.dto.HelloBean;


@RestController
public class HelloController {

	/*
	 * request:
	 *     curl -i "http://localhost:8083/hello?name=sy"
	 * response:
	 *     hello sy
	 */
    @ResponseBody
    @RequestMapping("/hello")
    public String hello(String name) {
    	System.out.println("HelloController hello. name=" + name);
    	return "hello " + name;
    }

	/*
	 * request:
	 *     curl -i "http://localhost:8083/helloBean"
	 * response:
	 *     {"code":"1","message":"name can not be null","name":null}
	 *     
	 * request:
	 *     curl -i "http://localhost:8083/helloBean?name=sy"
	 * response:
	 *     {"code":"0","message":null,"name":"sy"}
	 */
    @ResponseBody
    @RequestMapping("/helloBean")
    public HelloBean helloBean(String name) {
    	HelloBean helloBean = new HelloBean();
    	System.out.println("HelloController helloBean. name=" + name);
    	if(name==null) {
    		helloBean.setCode("1");
    		helloBean.setMessage("name can not be null");
    		helloBean.setName(null);
    	}
    	else {
    		helloBean.setCode("0");
    		helloBean.setMessage(null);
    		helloBean.setName(name);
    	}
    	return helloBean;
    }

}

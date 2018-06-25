package cn.sy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.sy.service.MyService;


@RestController
public class MyController {


	@Autowired
	MyService myService;

	/*
	 * request:
	 *     curl -i "http://localhost:8080/hello?name=sy"
	 * response:
	 *     hello sy
	 */
    @ResponseBody
    @RequestMapping("/ds/{ds}")
    public String hello(@PathVariable("ds") String ds) {
    	String result = null;
    	System.out.println("MyController /ds. ds=" + ds);
    	result = myService.getDs(ds);
    	return "ds: " + result;
    }

}

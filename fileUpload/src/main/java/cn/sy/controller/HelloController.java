package cn.sy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	/*
	 * request:
	 *     curl -i "http://localhost:8080/hello?name=sy"
	 * response:
	 *     hello sy
	 */
    @ResponseBody
    @RequestMapping("/hello")
    public String hello(String name) {
    	System.out.println("HelloController hello. name=" + name);
    	return "hello " + name;
    }


}

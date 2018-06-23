package cn.sy.controller;

import java.util.UUID;

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

    @ResponseBody
    @RequestMapping("/hash")
    public String hash(int n, int m) {
    	System.out.println("HelloController hash. n=" + n);
    	
    	char[] chars = {'A', 'C', 'D', 'I', 'P', 'X'};
    	int cLen = chars.length;
    	
    	int bucketSize = m;
    	int[] codes = new int[bucketSize];
    	int code = 0;
    	int idx = 0;
    	String str = null;
    	
    	for(int i=0;i<codes.length;i++) {
    		codes[i] = 0;
    	}
    	
    	for(int i=0;i<n;i++) {
//    		str = "CODE_" + chars[i%cLen] + i;
    		str = UUID.randomUUID().toString();
//    		code = str.hashCode();
    		code = (int)hash(str, 0, 9999);
//    		System.out.println(str + "  hashCode -> " + code);
        	idx = Math.abs(code) % bucketSize;
        	codes[idx]++;
    	}
    	
    	System.out.println("result:");
		for(int i=0;i<codes.length;i++) {
    		System.out.println(i + " -> " + codes[i]);
    	}
    	
    	return "hello ";
    }

    
    public static long hash(String s, int start, int end) {
		if (start < 0) {
			start = 0;
		}
		if (end > s.length()) {
			end = s.length();
		}
		long h = 0;
		for (int i = start; i < end; ++i) {
			h = (h << 5) - h + s.charAt(i);
		}
		return h;
	}
}

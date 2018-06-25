package cn.sy.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.sy.Message;

@RestController
public class HelloController {
	
	private static Logger logger = LoggerFactory.getLogger(HelloController.class);

	@Autowired
	private Message message;
	
	/**
	 * curl -i http://localhost:8080/hello
	 * @return
	 */
    @RequestMapping("/hello")
    @ResponseBody
    String hello() {
    	String titleKey = "nvms.task.message.0001.title";
		logger.info("start");
//		String titleCn = message.getForLocale(titleKey, Locale.SIMPLIFIED_CHINESE);
		String titleCn = message.getForLocale(titleKey, Locale.CHINESE);
        String titleUs = message.getForLocale(titleKey, Locale.US);
        
        String result = "titleCn=" + titleCn + "   titleUs=" + titleUs;
        return result;
    }

}

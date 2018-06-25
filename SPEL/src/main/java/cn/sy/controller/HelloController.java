package cn.sy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	private static Logger logger = LoggerFactory.getLogger(HelloController.class);

	/**
	 * curl -i http://localhost:8080/hello
	 * @return
	 */
    @RequestMapping("/hello")
    @ResponseBody
    String hello(String expr) {
    	logger.info("start expr: " + expr);

		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression(expr);
		String result = null;
		try {
			result = exp.getValue(String.class);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		

		logger.info("getValue " + result);
		
        return "expr: " + expr + ", result: " + result;
    }

}

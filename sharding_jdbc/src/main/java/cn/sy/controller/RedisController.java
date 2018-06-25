package cn.sy.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.sy.domain.TbProduct;
import cn.sy.domain.TbShop;
import cn.sy.domain.TbShopOrder;
import cn.sy.domain.TbUser;
import cn.sy.domain.TbUserOrder;
import cn.sy.service.TbService;


@RestController
@RequestMapping("/tb")
public class RedisController {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	/*
	 * 创建订单
	 * request:
	 *     curl -i "http://localhost:8083/tb/broadcast?msg=event"
	 * response:
	 *     OK
	 */
    @ResponseBody
    @RequestMapping("/broadcast")
    public String broadcast(String msg) {
    	System.out.println("RedisController broadcast."
    			+ " msg=" + msg);
    	
    	stringRedisTemplate.convertAndSend("MyChannel", msg);
    	
    	return "OK";
    }

}

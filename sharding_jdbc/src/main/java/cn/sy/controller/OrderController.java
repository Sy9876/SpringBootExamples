package cn.sy.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.sy.domain.Order;
import cn.sy.domain.OrderWithItem;
import cn.sy.domain.OrderWithItemAndPrice;
import cn.sy.domain.Shop;
import cn.sy.domain.ShopOrder;
import cn.sy.domain.User;
import cn.sy.dto.HelloBean;
import cn.sy.service.OrderService;
import cn.sy.service.ShopService;
import cn.sy.service.UserService;


@RestController
public class OrderController {

	private int[] users = { 1, 2, 3, 4, 5 };
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private ShopService shopService;

	
	/*
	 * 创建订单
	 * request:
	 *     curl -i "http://localhost:8083/neworder?user=1&item=1&shop=1"
	 * response:
	 *     OK
	 */
    @ResponseBody
    @RequestMapping("/neworder")
    public String neworder(int user, int item, int shop) {
    	System.out.println("OrderController order."
    			+ " user=" + user
    			+ " item=" + item
    			+ " shop=" + shop
   			 );
    	
    	orderService.createOrder(user, item, shop);
    	
    	return "OK";
    }

	/*
	 * 创建随机订单
	 * request:
	 *     curl -i "http://localhost:8083/neworders?count=10"
	 * response:
	 *     hello sy
	 */
    @ResponseBody
    @RequestMapping("/neworders")
    public String neworders(int count) {
    	System.out.println("OrderController neworders. count=" + count);
    	int user = 0;
    	int item = 0;
    	int shop = 0;
    	
    	// max user 
    	List<User> users = userService.findAll();
    	List<Shop> shops = shopService.findAll();
    	int userCnt = users.size();
    	int shopCnt = shops.size();
    	
    	Random random = new Random();
    	int rand = 0;
    	
    	for(int i=0;i<count;i++) {
    		
    		rand = Math.abs(random.nextInt());
    		user = rand % userCnt + 1;
    		shop = rand % shopCnt + 1;
    		item = rand % 10 + 1;
    		orderService.createOrder(user, item, shop);
    	}
    	
    	return "OK";
    }

    
    @ResponseBody
    @RequestMapping("/findAll")
    public List<Order> findAll() {
		List<Order> result = orderService.findAll();
		
		return result;
		
	}
    
    @ResponseBody
    @RequestMapping("/findById")
    public Order findById(int order_id) {
    	Order result = orderService.findById(order_id);
		
		return result;
		
	}
    
    
    @ResponseBody
    @RequestMapping("/findByUser")
    public List<Order> findByUser(int user_id) {
		List<Order> result = orderService.findByUser(user_id);
		
		return result;
		
	}
    
    @ResponseBody
    @RequestMapping("/findByShop")
    public List<ShopOrder> findByShop(int shop_id) {
		List<ShopOrder> result = orderService.findByShop(shop_id);
		
		return result;
		
	}
    
    @ResponseBody
    @RequestMapping("/findByShops")
    public List<ShopOrder> findByShop(String shops) {
    	String[] shopArr = shops.split(",");
    	List<Integer> shopList = new ArrayList<Integer>();
    	int shop_id = 0;
    	for(int i=0;i<shopArr.length;i++) {
    		shop_id = Integer.parseInt(shopArr[i]);
    		shopList.add(shop_id);
    	}
    	
		List<ShopOrder> result = orderService.findByShops(shopList);
		
		return result;
		
	}
    
    @ResponseBody
    @RequestMapping("/findOrderWithItem")
    public List<OrderWithItem> findOrderWithItem() {
		List<OrderWithItem> result = orderService.findOrderWithItem();
		
		return result;
		
	}
    
    
    @ResponseBody
    @RequestMapping("/findOrderWithPrice")
    public List<OrderWithItemAndPrice> findOrderWithPrice() {
    	List<OrderWithItemAndPrice> result = orderService.findOrderWithPrice();
//		StringBuffer sb = new StringBuffer();
//		for(Map<String, String> kv : result) {
//			
//		}
		return result;
		
	}
    
}

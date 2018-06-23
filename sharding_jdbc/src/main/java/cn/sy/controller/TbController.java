package cn.sy.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
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
public class TbController {

	@Autowired
	private TbService tbService;
	
	/*
	 * 创建订单
	 * request:
	 *     curl -i "http://localhost:8083/neworder?user=1&item=1&shop=1"
	 * response:
	 *     OK
	 */
    @ResponseBody
    @RequestMapping("/neworder")
    public String neworder(String user, String item, String shop) {
    	System.out.println("OrderController order."
    			+ " user=" + user
    			+ " item=" + item
    			+ " shop=" + shop
   			 );
    	
    	tbService.createOrder(user, item, shop);
    	
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
    	
    	// 
    	List<TbUser> userList = tbService.findAllUser();
    	int userCnt = userList.size();
    	List<TbShop> shopList = tbService.findAllShop();
    	int shopCnt = shopList.size();
    	List<TbProduct> productList = tbService.findAllProduct();
    	int productCnt = productList.size();
    	
    	
    	String user = null;
    	String item = null;
    	String shop = null;
    	
  	
    	Random random = new Random();
    	int rand = 0;
    	
    	for(int i=0;i<count;i++) {
    		
    		rand = Math.abs(random.nextInt());
    		user = userList.get(rand % userCnt).getId();
    		shop = shopList.get(rand % shopCnt).getShopCode();
    		item = productList.get(rand % productCnt).getId();
    		tbService.createOrder(user, item, shop);
    	}
    	
    	return "OK " + count + " orders";
    }

    
    @ResponseBody
    @RequestMapping("/findAllUserOrder")
    public List<TbUserOrder> findAllUserOrder() {
		List<TbUserOrder> result = tbService.findAllUserOrder();
		
		return result;
		
	}
    
    @ResponseBody
    @RequestMapping("/findAllShopOrder")
    public List<TbShopOrder> findAllShopOrder() {
		List<TbShopOrder> result = tbService.findAllShopOrder();
		
		return result;
		
	}
    
    @ResponseBody
    @RequestMapping("/findByIdUserOrder")
    public TbUserOrder findByIdUserOrder(String orderId) {
    	TbUserOrder result = tbService.findByIdUserOrder(orderId);
		
		return result;
		
	}
    
    @ResponseBody
    @RequestMapping("/findByIdShopOrder")
    public TbShopOrder findByIdShopOrder(String orderId) {
    	TbShopOrder result = tbService.findByIdShopOrder(orderId);
		
		return result;
		
	}
    
    @ResponseBody
    @RequestMapping("/findByUserUserOrder")
    public List<TbUserOrder> findById(String userId) {
    	List<TbUserOrder> result = tbService.findByUserUserOrder(userId);
		
		return result;
		
	}
    
    
    
}

package cn.sy.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.sy.domain.Shop;
import cn.sy.service.ShopService;


@RestController
public class ShopController {

	@Autowired
	ShopService shopService;

    @ResponseBody
    @RequestMapping("/shops")
    public List<Shop> shops() {
		List<Shop> result = shopService.findAll();
		
		return result;
		
	}
    
    @ResponseBody
    @RequestMapping("/shop")
    public Shop shop(int shop_id) {
    	Shop result = shopService.findById(shop_id);
		
		return result;
		
	}
    
    @ResponseBody
    @RequestMapping("/newShop")
    public int newShop(int shop_id, String shop_name) {
    	int result = shopService.newShop(shop_id, shop_name);
		
		return result;
		
	}
    

}

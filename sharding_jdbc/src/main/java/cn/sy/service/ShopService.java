package cn.sy.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sy.domain.Shop;
import cn.sy.mapper.ShopMapper;

@Service
public class ShopService {

	@Autowired
	private ShopMapper shopMapper;

	
	public List<Shop> findAll() {
		List<Shop> result = shopMapper.findAll();
		
		return result;
		
	}
	
	public Shop findById(int shop_id) {
		Shop result = shopMapper.findById(shop_id);
		
		return result;
		
	}	
	
	public int newShop(int shop_id, String shop_name) {
		int result = shopMapper.insertShop(shop_id, shop_name);
		
		return result;
		
	}
	
}

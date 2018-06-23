package cn.sy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sy.domain.Shop;
import cn.sy.domain.TbShopSharding;
import cn.sy.mapper.ShopMapper;
import cn.sy.mapper.TbShopShardingMapper;

@Service
public class ShopShardingService {
	private static Logger logger = Logger.getLogger(ShopShardingService.class);
	
	@Autowired
	private TbShopShardingMapper tbShopShardingMapper;

	private Map<String, String> routingMap = new HashMap<>();
	
	
	public void loadRoutingMap() {
//		routingMap.put("1", "1");
		
		logger.info("loadRoutingMap from db");
		
		routingMap.clear();
		List<TbShopSharding> list = tbShopShardingMapper.findAll();
		for(TbShopSharding tbShopSharding : list) {
			routingMap.put(tbShopSharding.getShopCode(), tbShopSharding.getTableIndex()+"");
		}
		
		logger.info("loadRoutingMap from db done. total " + list.size() + " entries");
	}
	
	public Map<String, String> getRoutingMap() {
		return routingMap;
	}
	
	public String getSharding(String shopCode) {
		String shard = routingMap.get(shopCode);
		if(shard==null) {
			shard="0";
		}
		return shard;
	}
}

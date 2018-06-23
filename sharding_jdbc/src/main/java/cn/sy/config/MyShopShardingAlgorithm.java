package cn.sy.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.sy.service.ShopShardingService;
import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

public class MyShopShardingAlgorithm implements PreciseShardingAlgorithm<String>,
	DependencyInjectListener {
	
	private static Logger logger = Logger.getLogger(MyShopShardingAlgorithm.class);
	
	private static int instanceCnt = 0;
	
	private ShopShardingService shopShardingService;
	
//	Map<String, String> routingMap = new HashMap<>();
	
	
	public void doDependencyInject() {
		
		this.shopShardingService = ApplicationContextHolder.getBean(ShopShardingService.class);
		logger.info("doDependencyInject done. shopShardingService=" + shopShardingService);
	}
	
	
	private void initLoading() {
		
//		shopShardingService = ApplicationContextHolder.getBean(ShopShardingService.class);
//		routingMap = shopShardingService.getRoutingMap();
		
//		routingMap.put("1", "1");
		
		ApplicationContextHolder.registerDependcy(this);

	}
	public MyShopShardingAlgorithm() {
		instanceCnt++;
		logger.info("constructor.  instanceCnt: " + instanceCnt + "  obj: " + this);
		initLoading();
	}
	
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
		
//		logger.info("doSharding. ");
//		String atn = printAvailableTargetNames(availableTargetNames);
//		logger.info(" availableTargetNames: " + atn);
//		
//		String sv =  printShardingValue(shardingValue);
//		logger.info(" shardingValue: " + sv);
		
		String v = shardingValue.getValue();
//		String result = routingMap.get(v);
		
		String result = shopShardingService.getSharding(v);
		
		if(result==null) {
			for(String s : availableTargetNames) {
				result = s;
				break;
			}
		}
		else {
			result = shardingValue.getLogicTableName() + "_" + result;
		}
		
//		logger.info(" routing result: " + result);
		return result;
	}

	private String printAvailableTargetNames(Collection<String> availableTargetNames) {
		StringBuffer sb = new StringBuffer();
		for(String name : availableTargetNames) {
			if(sb.length()>0) {
				sb.append(", ");
			}
			sb.append(name);
		}
		return sb.toString();
	}
	private String printShardingValue(PreciseShardingValue<String> shardingValue) {
		String s = new String();
		s += "logicTableName=" + shardingValue.getLogicTableName();
		s += ", columnName=" + shardingValue.getColumnName();
		s += ", value=" + shardingValue.getValue();
		
		return s;
	}
}

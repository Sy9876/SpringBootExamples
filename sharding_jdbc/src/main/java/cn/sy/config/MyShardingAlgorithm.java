package cn.sy.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

@Deprecated
public class MyShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {
	private static Logger logger = Logger.getLogger(MyShardingAlgorithm.class);
	
	Map<Integer, String> routingMap = new HashMap<>();
	
//	String defaultTableName = "t_shop_order_0";
	
	public MyShardingAlgorithm() {
		routingMap.put(1, "1");
		routingMap.put(2, "2");
		routingMap.put(3, "3");
		routingMap.put(4, "4");
		routingMap.put(5, "5");
		routingMap.put(6, "6");
		routingMap.put(7, "7");
		routingMap.put(8, "8");
		routingMap.put(9, "9");
		routingMap.put(10, "0");
		routingMap.put(11, "1");
		routingMap.put(12, "2");
		routingMap.put(13, "3");
		routingMap.put(14, "4");
		routingMap.put(15, "5");
		routingMap.put(16, "6");
		routingMap.put(17, "7");
		routingMap.put(18, "8");
		routingMap.put(19, "9");
		routingMap.put(20, "0");
	}
	
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
		
//		logger.info("doSharding. ");
//		String atn = printAvailableTargetNames(availableTargetNames);
//		logger.info(" availableTargetNames: " + atn);
//		
//		String sv =  printShardingValue(shardingValue);
//		logger.info(" shardingValue: " + sv);
		
		Integer v = shardingValue.getValue();
		String result = routingMap.get(v);
		
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
	private String printShardingValue(PreciseShardingValue<Integer> shardingValue) {
		String s = new String();
		s += "logicTableName=" + shardingValue.getLogicTableName();
		s += ", columnName=" + shardingValue.getColumnName();
		s += ", value=" + shardingValue.getValue();
		
		return s;
	}
}

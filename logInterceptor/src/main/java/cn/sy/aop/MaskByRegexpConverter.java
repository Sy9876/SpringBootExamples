package cn.sy.aop;

import org.apache.log4j.Logger;

/**
 * 按属性名屏蔽的转换器
 * 构造函数接收属性名或属性名数组，转换时会从json串中把包含这些属性的值屏蔽。
 * @author Administrator
 *
 */
public class MaskByRegexpConverter extends DefaultArgConverter {
	
	private static Logger logger = Logger.getLogger(MaskByRegexpConverter.class);
	
	private static final String MASK_STR = "\"...\"";
	
//	private List<String> propertyNames = new ArrayList<String>();
//	public MaskByRegexpConverter(String propertyName) {
//		propertyNames.add(propertyName);
//	}
//	public MaskByRegexpConverter(String[] propertyName) {
//		for(String prop : propertyName) {
//			propertyNames.add(prop);
//		}
//	}

	private String[] propertyNames = new String[1];
	public MaskByRegexpConverter(String propertyName) {
		propertyNames[0] = propertyName;
	}
	public MaskByRegexpConverter(String[] propertyNames) {
		this.propertyNames = propertyNames;
	}

	@Override
	public String onFinish(String argStr) {
		String resultStr = argStr;
		if(resultStr==null) {
			return null;
		}
		
		// 正则替换 "属性名":"[^"]*" 为 "属性名":"..."
		for(String propertyName : propertyNames) {
			String replaceFrom = "\"" + propertyName + "\":\"[^\"]*\"";
			String replaceTo = "\"" + propertyName + "\":" + MASK_STR;
			
			resultStr = resultStr.replaceAll(replaceFrom, replaceTo);
		}
		
		return resultStr;
	}

}

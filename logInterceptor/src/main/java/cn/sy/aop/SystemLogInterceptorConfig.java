package cn.sy.aop;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 配置系统日志拦截器
 * 有些请求参数包含敏感信息，因此需要使用特殊转换器屏蔽敏感信息
 * @author Administrator
 *
 */
@Component
public class SystemLogInterceptorConfig {
	private static Logger logger = Logger.getLogger(SystemLogInterceptorConfig.class);
	
	/**
	 * 默认转换器
	 */
	private ArgConverter defaultArgConverter = new DefaultArgConverter();
	
	/**
	 * url，转换器map
	 */
	private Map<String, ArgConverter> converterMap = new HashMap<String, ArgConverter>();
	
	/**
	 * 注册转换器
	 * @param urlPattern
	 * @param argConverter
	 */
	public void addConverter(String urlPattern, ArgConverter argConverter) {
		converterMap.put(urlPattern, argConverter);
	}
	
	/**
	 * 根据url，从converterMap中获取相应的转换器，
	 * 无匹配时返回默认转换器
	 * @param url
	 * @return
	 */
	public ArgConverter getConverter(String url) {
		ArgConverter converter = converterMap.get(url);
		if(converter==null) {
			converter = defaultArgConverter;
		}
		return converter;
	}
	
	/**
	 * 构造方法，初始化converterMap
	 */
	public SystemLogInterceptorConfig() {
		
		// 屏蔽login.do的第二参数
		converterMap.put("/v1/public/login.do", new MaskByArgIndexConverter(new int[] {1}));
		
		// 屏蔽changePassword.do参数dto中的 oldPassword 和 newPassword 属性
//		converterMap.put("/v1/public/changePassword.do", new DefaultArgConverter() {
//			@Override
//			public String onFinish(String argStr) {
//				String resultStr = null;
//				if(argStr==null) {
//					return null;
//				}
//				resultStr = argStr.replaceAll("\"oldPassword\":\"[^\"]*\"", "\"oldPassword\":\"...\"");
//				resultStr = resultStr.replaceAll("\"newPassword\":\"[^\"]*\"", "\"newPassword\":\"...\"");
//				return resultStr;
//			}
//		});
		
	}

}

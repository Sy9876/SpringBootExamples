package cn.sy.aop;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 参数转换器接口，把参数数组转换为字符串
 * @author Administrator
 *
 */
public interface ArgConverter {

	/**
	 * 转换方法
	 * @param args 参数数组
	 * @return 转换结果字符串
	 * @throws JsonProcessingException
	 */
	public String convert(Object[] args) throws JsonProcessingException;

}

package cn.sy.aop;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 参数转换器默认实现
 * 把参数数组转换为json串
 * @author Administrator
 *
 */
public class DefaultArgConverter implements ArgConverter {

	private ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 转换方法
	 * 接收参数数组，把每个参数转换为json串，然后使用逗号拼接起来。
	 * 会忽略参数中的BindingResult, HttpServletRequest等类型。
	 */
	public String convert(Object[] args) throws JsonProcessingException {
		
		if(args==null) {
			return null;
		}
		int argLen = args.length;
		if(argLen==0) {
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		
		// 把每个参数转换为JSON串，用逗号拼接
		List<String> argStrList = new ArrayList<String>();
		Object o = null;
		
		for(int i=0;i<argLen;i++) {
			String argStr = null;
			o = args[i];
			// 忽略 BindingResult, HttpServletRequest, HttpServletResponse 等不需要转换的参数
			if( o instanceof BindingResult ||
				o instanceof HttpServletRequest ||
				o instanceof HttpServletResponse ||
				o instanceof Model ||
				o instanceof MultipartFile ||
				o instanceof Part
				) {
				continue;
			}

			// 回调supportArg
			boolean isSupport = supportArg(i, o);
			if(!isSupport) {
				// 跳过不支持的参数
				continue;
			}
			// 回调onProcessArg
			argStr = onProcessArg(i, o);
			
			// 回调onPostProcessArg
			argStr = onPostProcessArg(i, o, argStr);
			
			argStrList.add(argStr);
//			logger.info("args: " + argStr);
		}
		if(argStrList.size()>0) {
			sb.append("args: " + String.join(",", argStrList));
		}
		
		String resultStr = sb.toString();
		// 回调onFinish
		resultStr = onFinish(resultStr);

    	return resultStr;

	}
	
	/**
	 * 是否支持此参数
	 * 子类可以覆盖此方法，对不希望转换的参数返回false
	 * @param argIdx 第几参数（从0开始）
	 * @param arg 参数对象
	 * @return true：支持此参数的转换；false：不支持，转换时会跳过此参数。
	 */
	public boolean supportArg(int argIdx, Object arg) {
		return true;
	}
	
	/**
	 * 把参数转换为json串
	 * 子类可以覆盖此方法，使用其它方式转换
	 * @param argIdx 第几参数（从0开始）
	 * @param arg 参数对象
	 * @return json串
	 * @throws JsonProcessingException
	 */
	public String onProcessArg(int argIdx, Object arg) throws JsonProcessingException {
		return mapper.writeValueAsString(arg);
	}
	
	/**
	 * 转换后处理
	 * 子类可以覆盖此方法，对转换后的结果进行处理，例如对转换结果的字符串进行正则替换等等。
	 * @param argIdx 第几参数（从0开始）
	 * @param arg 参数对象
	 * @param argStr 转换结果的字符串
	 * @return 处理后的字符串
	 */
	public String onPostProcessArg(int argIdx, Object arg, String argStr) {
		return argStr;
	}
	
	/**
	 * 最终处理
	 * 子类可以覆盖此方法，对最终转换结果再加工。
	 * @param argStr 整个参数数组转换后的字符串
	 * @return 处理后的字符串
	 */
	public String onFinish(String argStr) {
		return argStr;
	}

}

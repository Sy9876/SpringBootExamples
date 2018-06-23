package cn.sy.aop;

import java.security.Principal;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import cn.sy.domain.SSystemLog;
import cn.sy.mapper.SSystemLogMapper;

@Aspect
@Component
public class SystemLogInterceptor {
	private static Logger logger = Logger.getLogger(SystemLogInterceptor.class);
	
	private static final String TOKEN_HEADER_NAME = "x-auth-token";
	
	@Autowired
	private SSystemLogMapper systemLogMapper;
	
	@Autowired
	private SystemLogInterceptorConfig systemLogInterceptorConfig;
	
	// 拦截controller中带有RequestMapping注解的方法
	@Around("execution(* cn.sy..controller..*(..)) && ( @annotation(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping) )")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		Object rtn = null;
		try {
			logger.debug("joinPoint: " + joinPoint.toLongString());
			
			// 获取客户端ip
			String ipAddress = getRemoteAddress();
			
			// 获取请求url
			String url = getRequestUrl();
			
			// 获取token
			String token = getToken();
			
			// 获取登录账号
			String account = getAccount();

			// 获取请求详情
			String requestDetail = getRequestDetail(url, joinPoint);
			
			logger.debug("\nrequest info:"
					+ "\n  account: " + account
					+ "\n  url: " + url
					+ "\n  requestDetail: " + requestDetail
					+ "\n  token: " + token
					+ "\n  ipAddress: " + ipAddress
			);
			
			// 记录请求信息
	    	SSystemLog systemLog = new SSystemLog();
	    	systemLog.setAccount(account);
	    	systemLog.setOperation(url);
	    	systemLog.setOperationDetail(requestDetail);
	    	systemLog.setToken(token);
	    	systemLog.setIpAddress(ipAddress);
	    	systemLog.setOperationTime(new Date());
			saveSystemLog(systemLog);
			logger.debug("saveSystemLog success. id: " + systemLog.getId());
			
			// 调用被拦截的方法
			long start = System.currentTimeMillis();
			rtn = joinPoint.proceed();
			long end = System.currentTimeMillis();
			long timeMs = end - start;
			
			logger.info("completed in " + (timeMs) + " ms: " + joinPoint);
			
		} catch (Throwable e) {
			logger.error(e);
			throw e;
		}

		return rtn;
	}
	
    private String getRemoteAddress() {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
    	
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }
    
    private String getRequestUrl() {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
    	
        String requestUrl = request.getContextPath() + request.getServletPath();
        return requestUrl;  
    }
    
    private String getToken() {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
    	
//    	Enumeration<String> headers = request.getHeaderNames();
		String token = request.getHeader(TOKEN_HEADER_NAME);
		return token;  
    }
    
    private String getAccount() {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
    	Principal principal = request.getUserPrincipal();
    	if(principal==null) {
    		return null;
    	}
    	String account = principal.getName();
		return account;  
    }

    private String getRequestDetail(String url, ProceedingJoinPoint joinPoint) {
    	String detailStr = null;
		
    	// 获取方法签名
    	Signature signature = joinPoint.getSignature();
    	
    	// 转换参数 （有些请求参数包含敏感信息，需要使用特殊转换器屏蔽敏感信息）
		ArgConverter argConverter = systemLogInterceptorConfig.getConverter(url);
		
		String argStr = null;
		try {
			argStr = argConverter.convert(joinPoint.getArgs());
		} catch (Exception e) {
			// 转换错误不应该影响记录日志，按null处理
			logger.error("", e);
		}
		
		if(argStr==null || "".equals(argStr)) {
			detailStr = "method: " + signature;
		}
		else {
			detailStr = argStr + "  method: " + signature;
		}
		
		logger.debug("operation detail: " + detailStr);
		
    	return detailStr;
    }
    
    private void saveSystemLog(SSystemLog systemLog) {
    	try {
    		systemLogMapper.insert(systemLog);
    	} catch (Exception e) {
    		logger.error(e);
    	}
    	
    }
}

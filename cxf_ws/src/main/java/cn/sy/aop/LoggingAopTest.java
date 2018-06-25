package cn.sy.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAopTest {
	private static Logger logger = Logger.getLogger(LoggingAopTest.class);

	@Around("execution(* cn.sy.controller.UserController.*(..))")
	public Object logServiceAccess(ProceedingJoinPoint joinPoint) {
		Object rtn = null;
		try {
//			System.out.println("LoggingAopTest start: " + joinPoint);
			logger.info("LoggingAopTest start: " + joinPoint);
			
//			System.out.println("LoggingAopTest long string: " + joinPoint.toLongString());
			logger.info("LoggingAopTest long string: " + joinPoint.toLongString());
			
			for(Object o : joinPoint.getArgs()) {
				System.out.println("args: " + o);
				logger.info("args: " + o);
			}

			long start = System.currentTimeMillis();
			rtn = joinPoint.proceed();
			long end = System.currentTimeMillis();
			
//			System.out.println("LoggingAopTest Completed in " + (end - start) + " ms: " + joinPoint);
			logger.info("LoggingAopTest Completed in " + (end - start) + " ms: " + joinPoint);

		} catch (Throwable e) {
//			e.printStackTrace();
//			System.out.println("LoggingAopTest failed: " + joinPoint);
			logger.error(e);
			logger.info("LoggingAopTest failed: " + joinPoint);
		}
		
		return rtn;
	}

}

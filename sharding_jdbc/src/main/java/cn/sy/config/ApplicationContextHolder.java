package cn.sy.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import cn.sy.service.ShopShardingService;

@Component
public class ApplicationContextHolder implements ApplicationContextAware,
	ApplicationListener<ContextRefreshedEvent> {
	
	private static Logger logger = Logger.getLogger(ApplicationContextHolder.class);
	
	private static ApplicationContext applicationContext = null;
	
	private static List<DependencyInjectListener> listeners = new ArrayList<DependencyInjectListener>();
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextHolder.applicationContext = applicationContext;
//		System.out.println("ApplicationAware setApplicationContext start");

	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(requiredType);
	}


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
//		System.out.println("ApplicationAware onApplicationEvent start. event: " + event);
		
		logger.info("ApplicationAware onApplicationEvent start. event: " + event);
		
		doDependencyInject();
		
		ShopShardingService service = applicationContext.getBean(ShopShardingService.class);
		service.loadRoutingMap();
		logger.info("ApplicationAware onApplicationEvent ShopShardingService.loadRoutingMap done");
		
	}

	private void doDependencyInject() {
		for(DependencyInjectListener listener : listeners) {
			listener.doDependencyInject();
		}
	}
	public static void registerDependcy(DependencyInjectListener objRef) {
		listeners.add(objRef);
	}
}

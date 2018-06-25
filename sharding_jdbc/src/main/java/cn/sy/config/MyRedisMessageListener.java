package cn.sy.config;

import org.apache.log4j.Logger;

public class MyRedisMessageListener {
	private static Logger logger = Logger.getLogger(MyRedisMessageListener.class);
	
	/**
	 * 分片规则变化通知监听器
	 * 接收通知，刷新redis中的分片规则
	 * @param message
	 */
	public void receiveMessage(String message) {
		logger.info("Received <" + message + ">");
    }
}

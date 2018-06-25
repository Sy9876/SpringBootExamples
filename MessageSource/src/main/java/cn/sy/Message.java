package cn.sy;

import java.util.Locale;
import javax.annotation.Resource;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * @ClassName: Message
 * @Description: 获取消息
 * @author: Administrator
 */
@Component
public class Message {
    /**
     * 默认语言区域
     */
    private Locale locale = Locale.CHINESE;

    /**
     * Spring MessageSource实例
     */
    @Resource
    private MessageSource messageSource;

    /**
     * 创建一个新的实例 Message.
     */
    public Message() {

    }

    /**
     * 获取无参消息文本(默认locale)
     * 
     * @param key
     *            消息key
     * @return 消息文本
     */
    public String get(String key) {
        return messageSource == null ? null : messageSource.getMessage(key, null, locale);
    }

    /**
     * 获取有参消息文本(默认locale)
     * 
     * @param key
     *            消息key
     * @param argList
     *            参数List
     * @return 消息文本
     */
    public String get(String key, Object... argList) {
        return messageSource == null ? null : messageSource.getMessage(key, argList, locale);
    }

    
    /**
     * 指定语言，获取无参消息文本
     * 
     * @param key
     *            消息key
     * @param locale
     *            语言区域
     * @return 消息文本
     */
    public String getForLocale(String key, Locale locale) {
    	if(locale==null) {
    		return null;
    	}
    	return messageSource == null ? null : messageSource.getMessage(key, null, locale);
    }

    /**
     * 指定语言，获取有参消息文本
     * 
     * @param key
     *            消息key
     * @param locale
     *            语言区域
     * @param argList
     *            参数List
     * @return 消息文本
     */
    public String getForLocale(String key, Locale locale, Object... argList) {
    	if(locale==null) {
    		return null;
    	}
        return messageSource == null ? null : messageSource.getMessage(key, argList, locale);
    }
}

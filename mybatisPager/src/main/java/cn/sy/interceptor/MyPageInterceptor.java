package cn.sy.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

@Intercepts({
	@Signature(type=StatementHandler.class,
			method="prepare",
			args= {Connection.class, Integer.class})
})
public class MyPageInterceptor implements Interceptor {

	/**
	 * 需要注意目标对象可能被层层包裹。
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("MyPageInterceptor intercept: " + invocation);
		
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStmtHandler = SystemMetaObject.forObject(statementHandler);
		while(metaStmtHandler.hasGetter("h")) {
			Object object = metaStmtHandler.getValue("target");
			metaStmtHandler = SystemMetaObject.forObject(object);
		}
		
		String sql = (String) metaStmtHandler.getValue("delegate.boundSql.sql");
		String newSql;
		
		newSql=sql + " limit 100";
		
		metaStmtHandler.setValue("delegate.boundSql.sql", newSql);
		
		Object result = invocation.proceed();
		return result;
	}

	/**
	 * 通常plugin方法的写法是固定的，只是用Plugin.wrap包装一下。这样就可以拦截到目标方法进入intercept了。
	 */
	@Override
	public Object plugin(Object target) {
		System.out.println("MyPageInterceptor plugin: " + target);
		return Plugin.wrap(target, this);
	}

	/**
	 * 容器初始化时被调用。传入的是plugin配置的参数。
	 */
	@Override
	public void setProperties(Properties properties) {
		System.out.println("MyPageInterceptor setProperties: " + properties);
		
	}

}

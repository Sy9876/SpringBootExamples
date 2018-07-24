# springboot examples mybatisPager

## 自定义mybatis插件

实现org.apache.ibatis.plugin.Interceptor接口，

```
@Intercepts({
	@Signature(type=StatementHandler.class,
			method="prepare",
			args= {Connection.class, Integer.class})
})
public class MyPageInterceptor implements Interceptor {

  	// 拦截方法
  	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		return invocation.proceed();
	}

	// 使用Plugin.wrap包装
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}
  
	// 接收配置参数。容器初始化时被调用。
	@Override
	public void setProperties(Properties properties) {
	}
```

配置plugin

```
  		<plugin interceptor="cn.sy.interceptor.MyPageInterceptor">
  			<property name="dialect" value="mysql"/>
  		</plugin>
```






## Mybatis-PageHelper 

帮助页：<https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md>

```
		<dependency>
		    <groupId>com.github.pagehelper</groupId>
		    <artifactId>pagehelper</artifactId>
		</dependency>
```

在springboot环境中，可以使用starter。
但是，如果使用了mybatis-config.xml，则不建议使用starter，因为autoconfig还会自动配置一个plugin，导致环境中有多个插件而报错。

```
			<dependency>
			    <groupId>com.github.pagehelper</groupId>
			    <artifactId>pagehelper-spring-boot-starter</artifactId>
			</dependency>
```

### 自动获取总数

插件会拦截SQL，并增加一次count的调用，条件与原SQL相同。而且还会智能地去掉order by等不需要的操作。

配置rowBoundsWithCount=true

```
	    <plugin interceptor="com.github.pagehelper.PageInterceptor">
			<property name="dialect" value="sqlserver2012"/>
			<property name="rowBoundsWithCount" value="true"/>
		</plugin>
```

Mapper

```
	Page<Order> findByUserId(@Param("user_id") int user_id, PageRowBounds rowBounds);
```

DAO

```
	Page<Order> result = orderMapper.findByUserId(10, new PageRowBounds(1, 10));
	System.out.println("got PageInfo: "
		+ " getTotal: " + result.getTotal()
		+ " getPages: " + result.getPages()
		+ " getSize: " + result.getPageNum()
	);
```


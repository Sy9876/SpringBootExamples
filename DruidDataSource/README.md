# springboot examples druid dataSource

配置多数据源，使用Druid连接池。

Druid连接池默认的配置属性是

```
spring.datasource.druid.xxx，
```

当没有配置时，会使用这几个属性。

```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
spring.datasource.driverClassName=
```

当需要配置多数据源时，配置需要按名字分开，为此，需要用到 @ConfigurationProperties 注解修改属性映射。

这样，就把配置变成了

```
spring.datasource.druid.ds1.xxx=
spring.datasource.druid.ds2.xxx=
```

然后，需要禁用自动配置，并使用 @Configuration 和 @Bean 配置 DataSource，SqlSessionFactory 以及DataSourceTransactionManager。

```
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})


@Configuration
public class Config {
	@Bean(initMethod="init")
	@ConfigurationProperties("spring.datasource.druid.ds1")
	public DataSource dataSource() {
		DataSource ds = DruidDataSourceBuilder.create().build();
		return ds;
	}

```

使用sharding-jdbc时，自动配置 io.shardingjdbc.spring.boot.SpringBootConfiguration会
根据配置构造DataSource（beanName为dataSource，type为ShardingDataSource）

为了简便起见，保持sharding-jdbc自动配置的DataSource的beanName为dataSource。（怎么改beanName？）

把使用 @Configuration 和 @Bean 配置的其它 DataSource，命名为别的。





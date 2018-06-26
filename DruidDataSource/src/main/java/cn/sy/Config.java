package cn.sy;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

import io.shardingjdbc.core.jdbc.core.datasource.ShardingDataSource;

@Configuration
public class Config {

	/**
	 * 自定义数据源ds1 bean命名为dataSource1。 
	 * 注意：不能命名为dataSource，会与SpringBootConfiguration构造出的ShardingDataSource重名。
	 * 
	 * @return
	 */
	@Bean(initMethod="init")
	@ConfigurationProperties("spring.datasource.druid.ds1")
	public DataSource dataSource1() {
		DataSource ds = DruidDataSourceBuilder.create().build();
		return ds;
	}
	
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource1) throws Exception {
    	SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource1);
        return factory.getObject();
    }
    
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource1) {  
         return new DataSourceTransactionManager(dataSource1);  
    }
    
    
    
    
    
//	@Bean(initMethod="init")
//	@ConfigurationProperties("spring.datasource.druid.ds2")
//	public DataSource dataSource2() {
//		DataSource ds = DruidDataSourceBuilder.create().build();
//		return ds;
//	}

    /**
     *  ds2使用ShardingDataSource。直接使用限定名"dataSource"
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory2(@Qualifier("dataSource") DataSource dataSource2) throws Exception {
    	SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource2);
        return factory.getObject();
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager2(@Qualifier("dataSource") DataSource dataSource2) {  
         return new DataSourceTransactionManager(dataSource2);  
    }
    

}

package cn.sy;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
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

@Configuration
public class Config {

	
	@Bean(initMethod="init")
	@ConfigurationProperties("spring.datasource.druid.ds1")
	public DataSource dataSource() {
		DataSource ds = DruidDataSourceBuilder.create().build();
		return ds;
	}
	
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    	SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        return factory.getObject();
    }
    
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {  
         return new DataSourceTransactionManager(dataSource);  
    }
    
    
    
    
	@Bean(initMethod="init")
	@ConfigurationProperties("spring.datasource.druid.ds2")
	public DataSource dataSource2() {
		DataSource ds = DruidDataSourceBuilder.create().build();
		return ds;
	}
    
    @Bean
    public SqlSessionFactory sqlSessionFactory2(DataSource dataSource2) throws Exception {
    	SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource2);
        return factory.getObject();
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager2(DataSource dataSource2) {  
         return new DataSourceTransactionManager(dataSource2);  
    }
    

}

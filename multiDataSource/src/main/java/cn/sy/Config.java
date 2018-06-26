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

@Configuration
public class Config {

	/**
	 * 使用@ConfigurationProperties，定义prefix=xxx.ds1，
	 * 将ds1下的属性绑定到原来默认的spring.datasource上，创建DataSource
	 * 
	 * @return
	 */
	@Bean
	@ConfigurationProperties("spring.datasource.ds1")
	public DataSource dataSource() {
		DataSource ds = DataSourceBuilder.create().build();
		return ds;
	}
//    @Bean
//    public DataSource dataSource() {
//    	
//    	java.sql.Driver driver;
//		try {
//			driver = new com.mysql.cj.jdbc.Driver();
//			String url = "jdbc:mysql://127.0.0.1:3306/ds_1?useSSL=false&serverTimezone=UTC";
//	    	String username = "user";
//	    	String password = "user";
//	    	
//	    	return new SimpleDriverDataSource(driver, url, username, password);
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		}
//    	
//		return null;
//    }
    
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
    
    
	@Bean
	@ConfigurationProperties("spring.datasource.ds2")
	public DataSource dataSource2() {
		DataSource ds = DataSourceBuilder.create().build();
		return ds;
	}

//    @Bean
//    public DataSource dataSource2() throws Exception {
//    	
//    	java.sql.Driver driver;
//
//		driver = new com.mysql.cj.jdbc.Driver();
//		String url = "jdbc:mysql://127.0.0.1:3306/ds_2?useSSL=false&serverTimezone=UTC";
//    	String username = "user";
//    	String password = "user";
//    	
//    	return new SimpleDriverDataSource(driver, url, username, password);
//
//    }
    
    @Bean
    public SqlSessionFactory sqlSessionFactory2(DataSource dataSource2) throws Exception {
    	SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource2);
        return factory.getObject();
    }
    
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate2(SqlSessionFactory sqlSessionFactory2) {
//    	return new SqlSessionTemplate(sqlSessionFactory2);
//    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager2(DataSource dataSource2) {  
         return new DataSourceTransactionManager(dataSource2);  
    }
    

}

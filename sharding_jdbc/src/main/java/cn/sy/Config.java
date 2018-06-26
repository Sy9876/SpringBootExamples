package cn.sy;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class Config {

//	@Autowired
//	DataSource dataSource;
	
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
    	SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        return factory.getObject();
    }
    
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dataSource") DataSource dataSource) {  
         return new DataSourceTransactionManager(dataSource);  
    }

	@Bean
	@ConfigurationProperties("spring.datasource.ds2")
	public DataSource dataSource2() {
		DataSource ds = DataSourceBuilder.create().build();
		return ds;
	}

    @Bean
    public SqlSessionFactory sqlSessionFactory2(@Qualifier("dataSource2") DataSource dataSource2) throws Exception {
    	SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource2);
        return factory.getObject();
    }
    
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager2(@Qualifier("dataSource2") DataSource dataSource2) {  
         return new DataSourceTransactionManager(dataSource2);  
    }
	
}

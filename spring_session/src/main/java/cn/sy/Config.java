package cn.sy;

import java.util.Properties;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class Config {

	/**
	 * config DatabaseIdProvider in mybatis-config.xml do not work in spring mybatis.
	 * @return
	 */
	@Bean
	public DatabaseIdProvider getDatabaseIdProvider() {
		DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
		Properties p = new Properties();
		p.setProperty("Oracle", "oracle");
		p.setProperty("SQL Server", "sqlserver");
		p.setProperty("MySQL", "mysql");
		databaseIdProvider.setProperties(p);
		return databaseIdProvider;
	}
}

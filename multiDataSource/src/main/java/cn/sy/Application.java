package cn.sy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

/**
 * 使用exclude关闭DataSourceAutoConfiguratio，以便在Config中定义多数据源
 * @author Administrator
 *
 */
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
//@SpringBootApplication()
public class Application {

    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    	
    }

}

package cn.sy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

//@SpringBootApplication(exclude = {io.shardingjdbc.spring.boot.SpringBootConfiguration.class})
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
//@SpringBootApplication()
public class Application {

    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    	
    }

}

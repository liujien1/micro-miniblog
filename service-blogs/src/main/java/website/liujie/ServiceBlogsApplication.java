package website.liujie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ComponentScan({"website.liujie"})
@MapperScan("website.liujie.dao")
@EnableEurekaClient
public class ServiceBlogsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceBlogsApplication.class, args);
	}

	@RequestMapping("/test")
	public String test(){

		return "this is a restful return";
	}
}


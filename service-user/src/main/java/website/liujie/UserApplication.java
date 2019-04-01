package website.liujie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import redis.clients.jedis.JedisPool;
import website.liujie.util.SpringContextUtil;
import website.liujie.util.jedis.RedisUtil;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan("website.liujie")
@MapperScan("website.liujie.dao")
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}


}


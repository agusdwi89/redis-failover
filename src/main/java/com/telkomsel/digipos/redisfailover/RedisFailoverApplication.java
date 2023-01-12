package com.telkomsel.digipos.redisfailover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;

@SpringBootApplication(exclude = {
		RedisAutoConfiguration.class,
		RedisReactiveAutoConfiguration.class
})
public class RedisFailoverApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisFailoverApplication.class, args);
	}

}

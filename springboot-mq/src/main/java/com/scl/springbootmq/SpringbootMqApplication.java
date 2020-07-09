package com.scl.springbootmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 开启定时投递
public class SpringbootMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMqApplication.class, args);
	}

}

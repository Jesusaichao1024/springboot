package com.tensquare.gathering;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
@EnableCaching
@SpringBootApplication
@EnableEurekaClient
public class GatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatherApplication.class, args);
	}

	/**
	 * 添加逐渐id值
	 * @return
	 */
	@Bean
	public IdWorker idWorker(){
		return new IdWorker(1, 1);
	}
	
}

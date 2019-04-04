package com.tensquare.article;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
import util.JwtUtil;

/**
 * @author ztq161024zsc
 */
@SpringBootApplication
@EnableEurekaClient
public class ArticleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticleApplication.class, args);
	}

	/**
	 * @return
	 */
	@Bean
	public IdWorker idWorker(){
		return new IdWorker(1, 1);
	}

	@Bean
	public JwtUtil jwtUtil() {
		return new JwtUtil();
	}
}

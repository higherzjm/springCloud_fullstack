package com.allen.springcloud.feign;

import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 该配置是为了实例化合约2
 * 这边的配置会向spring容器中注入自定义注解翻译器的合约
 * 该合约是对自定义注解的解析
 */
@Configuration //该注解声明该类是spring的一个配置类
public class FeignContract2Config {

	/**
	 * 向spring容器中注入feign自定义翻译器的bean
	 * @return
	 */
	@Bean
	public Contract feignContract() {
		return new FeignDiyContract2();
	}
}

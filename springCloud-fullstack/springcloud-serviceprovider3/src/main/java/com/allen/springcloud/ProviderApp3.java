package com.allen.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderApp3 {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ProviderApp3.class).web(true).run(args);
	}

}

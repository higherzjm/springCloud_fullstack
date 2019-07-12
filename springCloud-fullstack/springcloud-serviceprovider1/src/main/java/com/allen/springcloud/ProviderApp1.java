package com.allen.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderApp1 {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ProviderApp1.class).web(true).run(args);
	}

}

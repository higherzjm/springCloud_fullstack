package com.allen.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient //开启eureka客户端服务
@EnableFeignClients //开启feign客户端服务
@EnableCircuitBreaker//开启circuitBreaker容错服务
@ServletComponentScan //添加此注解Hystrix过滤器才会生效
@EnableHystrixDashboard// 启动Hystrix监控
@EnableZuulProxy //打开Zuul客户端功能的注解
//@ComponentScan("com.allen.*")
public class InvokerApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(InvokerApp.class).web(true).run(args);
	}

}

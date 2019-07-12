package com.allen.springcloud.hystrix;

import org.springframework.stereotype.Component;

/**
 * 这是回退逻辑的bean
 * @Component声明这是一个spring容器里面的bean
 */
@Component//声明这个spring容器里面的一个bean
public class HelloClientFallback implements HytrixHelloClient {


	public String springCloudHystrixFeign_test1() {
		return "feign、hystrix与springCloud的整合测试1(服务调用超时)";
	}
	public String springCloudHystrixFeign_test2(String name) {
		return "feign、hystrix与springCloud的整合测试2(服务调用超时)："+name;
	}
}

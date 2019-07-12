package com.allen.springcloud.hystrix;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 会向spring容器中注入springboot-serviceprovider3服务对应服务方法的回退函数
 */
@FeignClient(name = "springboot-serviceprovider3", fallback = HelloClientFallback.class)
public interface HytrixHelloClient {

	 @RequestMapping(method = RequestMethod.GET, value = "/springCloudHystrixFeign_test1")
	 String springCloudHystrixFeign_test1();

	/**
	 *这边feign带传参的接口必须要用@PathVariable注解定义好参数名称
	 * @param name
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/springCloudHystrixFeign_test2/{name}")
	String springCloudHystrixFeign_test2(@PathVariable("name") String name);


}

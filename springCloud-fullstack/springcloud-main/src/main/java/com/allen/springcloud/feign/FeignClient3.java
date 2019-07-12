package com.allen.springcloud.feign;

import feign.Param;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 与springCloud整合使用调用的接口
 */
@FeignClient("springboot-serviceprovider")//声明服务提供者的id
public interface FeignClient3 {
	/**
	 * feign默认的注解翻译器会把@RequestMapping(..)注解翻译为
	 * 服务提供者的@RequestLine(..)注解信息
	 * 这边除了使用自定义注解只能使用spring的注解，
	 * 自定义注解暂时不支持传参的请求
	 */
	@RequestMapping(method = RequestMethod.GET, value="/getPolice/{id}")
	 Police getPolice(@PathVariable("id") Integer id); //返回rest风格的对象

	@FeignDiyInterfaceRule(url = "/baseRibbonServer", method = "GET")//返回string风格的字符串
	 String baseRibbonServer();


}

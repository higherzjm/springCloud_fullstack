package com.allen.springcloud.feign;


/**
 * :自定义注解翻译器调用的接口
 */
public interface FeignClient2 {

	@FeignDiyInterfaceRule(url = "/restTemplateTestServer1", method = "GET")//返回string风格的字符串
	public String restTemplateTestServer1();
}

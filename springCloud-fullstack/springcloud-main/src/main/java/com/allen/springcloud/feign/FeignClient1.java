package com.allen.springcloud.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * 默认客户端和自定义客户端调用都接口
 */
public interface FeignClient1 {
	@RequestLine("GET /restTemplateTestServer1")//返回string风格的字符串
	public String restTemplateTestServer1();

	@RequestLine("GET /getPolice/{id}")//返回rest风格的对象
	public Police getPolice(@Param("id") Integer id);

	@RequestLine("POST /createPolice")
	@Headers("Content-Type: application/json")//声明返回值类型为json字符串
	public String createPolice(Police p);


}

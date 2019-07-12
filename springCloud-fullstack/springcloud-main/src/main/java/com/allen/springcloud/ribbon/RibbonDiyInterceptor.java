package com.allen.springcloud.ribbon;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * 自定义拦截器代码
 */
public class RibbonDiyInterceptor implements ClientHttpRequestInterceptor {

	public ClientHttpResponse intercept(HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution) throws IOException {
		//System.out.println("===============  这是自定义拦截器");
		System.out.println("         旧的uri：" + request.getURI());
		
		HttpRequest newRequest = new RibbonDiyRequest(request);
		System.out.println("         新的uri:" + newRequest.getURI());
		
		return execution.execute(newRequest, body);//旧请求转换为新的请求
	}

}

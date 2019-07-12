package com.allen.springcloud.feign;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import feign.Client;
import feign.Request;
import feign.Response;
import feign.Request.Options;

/**
 * feign的自定义客户端
 */
public class FeignDiyClient implements Client {

	/**feign request对象 转换为HTTPclient request对象
	 * HttpClient的响应对象转换为Feign的Response
	 * 请求转请求，响应转响应
	 */
	public Response execute(Request request, Options options)
			throws IOException {
		System.out.println("this is my client");
		try {
			// 创建一个默认的客户端
			CloseableHttpClient httpclient = HttpClients.createDefault();
			// 获取调用的HTTP方法
			final String method = request.method();
			// 创建一个HttpClient的HttpRequest
			HttpRequestBase httpRequest = new HttpRequestBase() {
				public String getMethod() {
					return method;
				}
			};
			// 设置请求地址
			httpRequest.setURI(new URI(request.url()));
			// 执行请求，获取响应
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			// 获取响应的主体内容
			byte[] body = EntityUtils.toByteArray(httpResponse.getEntity());
			// 将HttpClient的响应对象转换为Feign的Response
			Response response = Response.builder()
					.body(body)
					.headers(new HashMap<String, Collection<String>>())
					.status(httpResponse.getStatusLine().getStatusCode())
					.build();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
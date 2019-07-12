package com.allen.springcloud.feign;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@Configuration
public class FeignController {

	@Autowired
	private FeignClient3 feignClient3;//注入服务调用的接口



	/**
	 * Apache HttpClients服务调用测试1，此方法是为了对比feign接口调用的便捷性
	 * @return
	 */
	//http://localhost:9000/httpClientstServerInvoke_test1
	@GetMapping("/httpClientstServerInvoke_test1")
	@ResponseBody
	public String httpClientstServerInvoke_test1(){
		StringBuffer result=new StringBuffer();
		String ret="";
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
		String time=simpleDateFormat.format(new Date());
		CloseableHttpClient httpClient= HttpClients.createDefault();
		try {
			// 请求超时
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(60000).build();//设置请求和传输超时时间
			// 目标地址
			HttpPost httppost = new HttpPost("http://127.0.0.1:8080/restTemplateTestServer1");
			System.out.println("请求: " + httppost.getRequestLine());
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("name","李四");
			// 构造最简单的字符串数据
			StringEntity reqEntity = new StringEntity(params.toString(),"UTF-8");
			// 设置类型
			reqEntity.setContentType("application/x-www-form-urlencoded");//form表单
			// 设置请求的数据
			httppost.setEntity(reqEntity);
			httppost.setConfig(requestConfig);
			// 执行
			CloseableHttpResponse response = httpClient.execute(httppost);
			HttpEntity entity = response.getEntity();
			System.out.println(response.getStatusLine());
			if (entity != null) {
				System.out.println("Response content length: " + entity.getContentLength());
			}
			// 显示结果
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			while ((ret = reader.readLine()) != null) {
				result.append(ret.trim()+",");
			}
			response.close();
			httpClient.close();
			ret=time+":Apache HttpClients服务调用测试1(不可传参)正常:"+result.toString().trim();
		}catch (Exception e){
			e.printStackTrace();
			ret=time+":Apache HttpClients服务调用测试1(不可传参)异常:";
		}
		return ret;
	}
	/**
	 * Apache HttpClients服务调用测试2，此方法是为了对比feign接口调用的便捷性
	 * @return
	 */
	//http://localhost:9000/httpClientstServerInvoke_test2
	@GetMapping("/httpClientstServerInvoke_test2")
	@ResponseBody
	public String httpClientstServerInvoke_test2(){
		String ret="";
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
		String time=simpleDateFormat.format(new Date());
		try{
			HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/restTemplateTestServer1");
			//param参数，可以为param="key1=value1&key2=value2"的一串字符串,或者是jsonObject
			//JSONObject param= new JSONObject();
			//param.put("name", "李四");
			String param="name=allen&age=30";
			StringEntity stringEntity = new StringEntity(param);
			stringEntity.setContentType("application/x-www-form-urlencoded");

			httpPost.setEntity(stringEntity);

			HttpClient client = new DefaultHttpClient();

			HttpResponse httpResponse = client.execute(httpPost);
			 ret = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
			ret=time+":Apache HttpPost服务调用测试2(可传参)正常:"+ret;

		} catch(IOException e){
			e.printStackTrace();
			ret=time+":Apache HttpPost服务调用测试2(可传参)异常:";
		}
		return ret;
	}
	/**
	 * Apache webClient服务调用测试1，此方法是为了对比feign接口调用的便捷性
	 * @return
	 */
	//http://localhost:9000/webClientServerInvoke_test1
	@GetMapping("/webClientServerInvoke_test1")
	@ResponseBody
	public String webClientServerInvoke_test1(){
		String content1="",content2="",ret="";
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
		String time=simpleDateFormat.format(new Date());
		try {
			// 创建WebClient
			WebClient client = WebClient.create("http://127.0.0.1:8081/restTemplateTestServer2/2019");
			// 获取响应
			Response response = client.get();
			// 获取响应内容
			InputStream ent = (InputStream) response.getEntity();
			 content1= IOUtils.readStringFromStream(ent);
			// 输出字符串
			System.out.println("content1:"+content1);

			client = WebClient.create("http://127.0.0.1:8080/restTemplateTestServer1");
			 response = client.get();
			 ent = (InputStream) response.getEntity();
			 content2= IOUtils.readStringFromStream(ent);
			 System.out.println("content2:"+content2);
			ret=time+":Apache webClient服务调用测试1:"+content1+"------------------------"+content2;
		} catch (IOException e) {
			e.printStackTrace();
			ret=time+":Apache webClient服务调用测试1:请求出现异常!";
		}
		return ret;
	}
	/**
	 * feign接口调用测试1:采取默认的客户端
	 * 使用了GsonDecoder()解码器,将http请求返回结果转换为对象
	 * 使用了GsonEncoder()编码器,将http请求返回结果转换json字符串
	 */
	//http://localhost:9000/feignServerInvoke_test1
	@GetMapping("/feignServerInvoke_test1")
	@ResponseBody
	public String feignServerInvoke_test1(){
		String content1="",content2="",content3="",ret="";
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
		String time=simpleDateFormat.format(new Date());
		try {
			FeignClient1 client = Feign.builder().target(FeignClient1.class,
					"http://localhost:8080");
			content1 = client.restTemplateTestServer1();

			/**
			 * GsonEncoder()编码器,http请求返回结果转换json字符串
			 * GsonDecoder()解码器,http请求返回结果转换为对象
			 */
			 client = Feign.builder()
					.decoder(new GsonDecoder())
					.target(FeignClient1.class,
							"http://localhost:8081");
			Police p = client.getPolice(2019);
			content2=p.toString();


			 client = Feign.builder()
					.encoder(new GsonEncoder())
					.target(FeignClient1.class,
							"http://localhost:8081");
			p=new Police();
			p.setId(2018);
			p.setMessage("feign 接口调用测试返回json数据格式message字段");
			p.setName("feign 接口调用测试返回json数据格式name字段");
			content3=client.createPolice(p);
			ret=time+":feign接口调用测试1:"+content1+"----------"+content2+"----------"+content3;
		} catch (Exception e) {
			e.printStackTrace();
			ret=time+":feign接口调用测试1:请求出现异常!";
		}
		return ret;
	}

	/**
	 * feign接口调用测试2:自定义客户端
	 * 使用了GsonDecoder()解码器,将http请求返回结果转换为对象
	 * @return
	 */
	//http://localhost:9000/feignServerInvoke_test2
	@GetMapping("/feignServerInvoke_test2")
	@ResponseBody
	public String feignServerInvoke_test2(){
		String content1="",content2="",ret="";
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
		String time=simpleDateFormat.format(new Date());
		try {
			FeignClient1 client = Feign.builder().client(new FeignDiyClient())//调用自定义客户端
					.target(FeignClient1.class,
					"http://localhost:8080");
			content1 = client.restTemplateTestServer1();

			/**
			 * GsonEncoder()编码器,http请求返回结果转换json字符串
			 * GsonDecoder()解码器,http请求返回结果转换为对象
			 */
			client = Feign.builder().client(new FeignDiyClient())
					.decoder(new GsonDecoder())
					.target(FeignClient1.class,
							"http://localhost:8081");
			Police p = client.getPolice(2019);
			content2=p.toString();

			ret=time+":feign接口调用测试2(自定义webclient客户端):"+content1+"----------"+content2;
		} catch (Exception e) {
			e.printStackTrace();
			ret=time+":feign接口调用测试2:请求出现异常!";
		}
		return ret;
	}

	/**
	 * feign接口调用测试3:使用了自定义客户端和自定义注解翻译器
	 */
	//http://localhost:9000/feignServerInvoke_test3
	@GetMapping("/feignServerInvoke_test3")
	@ResponseBody
	public String feignServerInvoke_test3(){
		String content="";
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
		String time=simpleDateFormat.format(new Date());
		try {
			FeignClient2 helloClient2 = Feign.builder().client(new FeignDiyClient())//调用自定义客户端
					.contract(new FeignDiyContract1())//调用自定义翻译器
					.target(FeignClient2.class,
							"http://localhost:8080");
			content = helloClient2.restTemplateTestServer1();

			content=time+":feign接口调用测试3(自定义注解翻译器):"+content;
		} catch (Exception e) {
			e.printStackTrace();
			content=time+":feign接口调用测试3:请求出现异常!";
		}
		return content;
	}

	/**
	 * feign接口调用测试4:feign与springCloud的整合，会自带负载均衡的功能
	 * spring容器注入了自定义负载均衡规则，该规则访问会偏向8080端口
	 */
	//http://localhost:9000/feignServerInvoke_test4
	@GetMapping("/feignServerInvoke_test4")
	@ResponseBody
	public String feignServerInvoke_test4(){
		String content="";
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
		String time=simpleDateFormat.format(new Date());
		try {
             for (int i=0;i<100;i++){
				 content=feignClient3.baseRibbonServer()+"--------------"+content;
			 }
			 content=time+":feign接口调用测试4(自定义注解翻译器与springCloud的整合，自带负载均衡的功能):"+content;

		} catch (Exception e) {
			e.printStackTrace();
			content=time+":feign接口调用测试4:请求出现异常!";
		}
		return content;
	}

}

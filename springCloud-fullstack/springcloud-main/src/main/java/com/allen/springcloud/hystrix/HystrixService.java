package com.allen.springcloud.hystrix;

import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

@Service
//@DefaultProperties(defaultFallback = "getMemberFallback") //全局的配置
public class HystrixService {


	/**
	 * @Qualifier 因容器中存在多个restTemplate对象类型的bean，
	 * 需要指明注入具体哪一个
	 */
	@Autowired
	@Qualifier("getRestTemplate")
    private RestTemplate restTemplate;

	//hystrix服务熔断测试降级测试 begin------------------------------

	/**
	 * fallbackMethod 回退方法
	 * commandProperties 命令参数
	 * threadPoolProperties 线程池参数
	 */
	@HystrixCommand(fallbackMethod = "getMemberFallback", groupKey = "MemberGroup", commandKey = "MemberCommandKey",
			commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")//配置超时时间
	}, threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "2")//配置线程池大小
	})
	public String hystrixTest1(String str) {
		try {
			Thread.sleep(2000);//休眠时间超过3000毫秒就会进入回退降级逻辑
		} catch (Exception e) {
			
		}
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
		String time=simpleDateFormat.format(new Date());
		String ret="",content="";
		try {
			for(int i = 0; i < 100; i++) {
				content= restTemplate.getForObject("http://springboot-serviceprovider/baseRibbonServer", String.class);
				ret=ret+"-----------"+content;
			}
			return time+":"+str+"正常:"+ret;
		}catch (Exception e){
			e.printStackTrace();
			return  time+":"+str+"异常";
		}
	}

	/**
	 * 假如服务提供者断掉(member app),就会调用此回退方法
	 * @return
	 */
	public String getMemberFallback(String str) {
		System.out.print("回退方法的调用");
		return str+":请求超时，这是回退逻辑!!";
	}
   //hystrix服务熔断测试降级测试 end------------------------------


	//hystrix缓存的测试 begin------------------------------
	/**
	 * 定义缓存的key
	 */
	@CacheResult
	@HystrixCommand(commandKey = "cacheKey")
	public String getCache(Integer id) {
		System.out.println("调用service方法:"+id);
		String content= restTemplate.getForObject("http://springboot-serviceprovider/baseRibbonServer", String.class);
		return content+":"+id;
	}

	/**
	 * 删除缓存，key必须要一样才有效果,且参数值要和读取缓存时的一样
	 */
	@CacheRemove(commandKey = "cacheKey")
	@HystrixCommand
	public void removeCache(Integer id) {
		System.out.println("删除缓存方法:"+id);
	}

	//hystrix缓存的测试 end------------------------------


    //hystrix请求合并的测试 begin------------------------------
	/**
	 * 该方法不会实际执行，只用于注解配置
	 * batchMethod 配置收集器的方法
	 * @HystrixProperty 配置指定时间内的请求,1000表示1秒内的请求
	 */
	@HystrixCollapser(batchMethod = "restTemplateTestServer2s", collapserProperties = {
			@HystrixProperty(name = "timerDelayInMilliseconds", value = "1000")
	})
	public Future<String> restTemplateTestServer2(Integer id) {
		System.out.println("执行单个查询的方法--该方法不会实际执行，只用于注解配置");
		return null;
	}

	/**
	 * 请求收集器由restTemplateTestServer2(Integer id)方法处理
	 * 实际执行该方法，收集对应传入的id参数
	 */
	@HystrixCommand
	public List<String> restTemplateTestServer2s(List<Integer> ids) {
		List<String> strs = new ArrayList<String>();
		System.out.println("ids size:"+ids.size()+";"+ids);
		for(Integer id : ids) {
			System.out.println(id);
			String str = restTemplate.getForObject("http://springboot-serviceprovider/baseRibbonServer", String.class);
			strs.add(str+":"+id);
		}
		return strs;
	}
	//hystrix请求合并的测试 end--------------------------------


	//hystrix 与 feign整合测试begin-----------------------

	@Autowired
	private HytrixHelloClient hytrixHelloClient;

	public String springCloudHystrixFeign_test1(){
		String ret="",content="";
		for (int i=0;i<5;i++){
			HystrixCircuitBreaker breaker = HystrixCircuitBreaker.Factory
					.getInstance(HystrixCommandKey.Factory
							.asKey("HytrixHelloClient#baseHytrixServer_test1()"));
			if (breaker!=null) {
				//返回true表示超时短路了，返回false则表示正常
				System.out.println("断路器状态：" + breaker.isOpen());
			}
	        content=hytrixHelloClient.springCloudHystrixFeign_test1();
			ret=ret+"--"+content;
		}
	   return ret;
	}

	public String springCloudHystrixFeign_test2(String name){
		String ret="",content="";
		for (int i=0;i<5;i++){
			HystrixCircuitBreaker breaker = HystrixCircuitBreaker.Factory
					.getInstance(HystrixCommandKey.Factory
							.asKey("HytrixHelloClient#baseHytrixServer_test1()"));
			if (breaker!=null) {
				//返回true表示超时短路了，返回false则表示正常
				System.out.println("断路器状态：" + breaker.isOpen());
			}
			content=hytrixHelloClient.springCloudHystrixFeign_test2(name);
			ret=ret+"--"+content;
		}
		return ret;
	}
	//hystrix 与 feign整合测试end-----------------------

}
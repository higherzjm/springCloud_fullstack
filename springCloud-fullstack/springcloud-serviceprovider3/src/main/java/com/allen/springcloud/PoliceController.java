package com.allen.springcloud;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class PoliceController {

	/**
	 * feign与hystrix的整合测试1
	 * @return
	 */
	@RequestMapping("/springCloudHystrixFeign_test1")
	public String springCloudHystrixFeign_test1()throws Exception{
		Thread.sleep(2000);
		return "feign、hystrix与springCLoud的整合测试1(服务正常调用)";
	}
	/**
	 * feign与hystrix的整合测试2
	 * @param request
	 * @return
	 */
	@RequestMapping("/springCloudHystrixFeign_test2/{name}")
	public String baseHytrixServer_test2(@PathVariable String name, HttpServletRequest request)throws Exception{
		 System.out.println("name:"+name);
         String name2=request.getParameter("name");//输出为null
		 System.out.println("name2:"+name2);
		 Thread.sleep(1000);
		return "feign、hystrix与springCLoud的整合测试2(服务正常调用):"+request.getRequestURL().toString();
	}


	/**
	 * 网关服务提供者测试1
	 * @return
	 */
	@RequestMapping("/zuulServer_test1/{name}")
	public String zuulServer_test1(@PathVariable String name,HttpServletRequest request)throws Exception{
		String age=request.getParameter("year");
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
		String time=simpleDateFormat.format(new Date());
		return time+":网关服务提供者测试1:"+name+":"+age;
	}
	/**
	 * 网关服务提供者测试2
	 * @return
	 */
	@RequestMapping("/zuulServer_test2")
	public String zuulServer_test2()throws Exception{
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
		String time=simpleDateFormat.format(new Date());
		return time+":网关服务提供者测试2";
	}
}

package com.allen.springcloud;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PoliceController {

	//http://localhost:8080/restTemplateTestServer1
	@RequestMapping("/restTemplateTestServer1")
	public String restTemplateTestServer1(HttpServletRequest request){
		String name=request.getParameter("name");
		return "restTemplate测试_服务提供者1:用服务提供方的服务id访问的;name:"+name;
	}

	/**
	 * 简单负载均衡服务调用者1
	 * @param request
	 * @return
	 */
	@RequestMapping("/baseRibbonServer")
	public String baseRibbonServer(HttpServletRequest request){

		return "简单负载均衡服务提供者1:"+request.getRequestURL().toString();
	}
}

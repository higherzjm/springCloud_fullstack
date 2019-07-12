package com.allen.springcloud;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class PoliceController {
    //http://localhost:8081/restTemplateTestServer2/2019
	@RequestMapping(value = "/restTemplateTestServer2/{id}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Police restTemplateTestServer2(@PathVariable Integer id, HttpServletRequest request) {
        String name=request.getParameter("name");
        System.out.println("name:"+name);
		Police p = new Police();
		p.setId(id);
		p.setName("restTemplate测试_服务提供者2:用服务提供方的ip和端口号访问的");
		p.setMessage(request.getRequestURL().toString());
		return p;
	}
	//http://localhost:8081/getPolice/2019
	@RequestMapping(value = "/getPolice/{id}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Police getPolice(@PathVariable Integer id, HttpServletRequest request) {
		Police p = new Police();
		p.setId(id);
		p.setName("feign接口调用的测试，服务提供者2");
		p.setMessage(request.getRequestURL().toString());
		return p;
	}
	/**
	 * 简单负载均衡服务调用者2
	 * @param request
	 * @return
	 */
	@RequestMapping("/baseRibbonServer")
	public String baseRibbonServer(HttpServletRequest request){

		return "简单负载均衡服务提供者2:"+request.getRequestURL().toString();
	}

	/**
	 * feign接口调用测试，返回json字符串数据格式类型
	 * @param p
	 * @return
	 */
	@RequestMapping(value = "/createPolice", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public String createPolice(@RequestBody Police p) {
		System.out.println(p.getMessage() + "---" + p.getName());
		return "服务提供者2：feign接口调用测试，返回json字符串数据格式类型, id: " + p.getId()+",name: "+p.getName()+",message: "+p.getMessage();
	}
}

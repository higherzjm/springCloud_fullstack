package com.allen.springcloud.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by allen on 2019/7/11.
 */
@RestController
public class RestTemplateController {
    @Autowired
    @Qualifier("getBaseRestTemplate")
    private RestTemplate getBaseRestTemplate;//只能以服务提供者的IP和端口号访问服务信息

    @Autowired
    @Qualifier("getRestTemplate")
    private RestTemplate restTemplate;//可以以服务提供者的服务id访问服务信息

    /**
     * 简单rest服务调用1,返回简单的字符串类型
     * 以服务id访问服务提供者的信息
     */
    //http://localhost:9000/baseRest_test1
    @GetMapping("/baseRest_test1")
    @ResponseBody
    public String baseRest_test1() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        String time=simpleDateFormat.format(new Date());
        RestTemplate tpl = restTemplate;
        String str = tpl.getForObject("http://springboot-serviceprovider/restTemplateTestServer1", String.class);
        return time+":"+str;
    }

    /**
     * 简单rest服务调用2,返回简单的对象类型
     * 以服务都ip和端口号访问服务提供者都信息
     */
    //http://localhost:9000/baseRest_test2/2019
    @RequestMapping(value = "/baseRest_test2/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String baseRest_test2(@PathVariable Integer id) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        String time=simpleDateFormat.format(new Date());
        RestTemplate tpl = getBaseRestTemplate;
        String str = tpl.getForObject("http://127.0.0.1:8081/restTemplateTestServer2/"+id, String.class);
        return time+":"+str;
    }

}

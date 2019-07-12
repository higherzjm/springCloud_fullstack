package com.allen.springcloud.zuul;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by allen on 2019/7/9.
 * 路由项目，重在配置
 * 原则上只提供转发路由的功能
 * 项目本身可以不存在服务
 *
 * 注意类名命名规则的问题，这边如果命名以Zuul开头好像会报错
 */
@RestController
public class MyZuulController {
    /**
     * 简单路由的测试
     * 简单路由可以省略path配置,没有path的话sourcezjm当后缀直接访问
     * http://localhost:9000/sourcezjm/zuulServer_test1/张三
     * 该路径根据yml配置会转发的路径为
     *  http://localhost:8082/zuulServer_test1/张三
     * 请求路径要跟网关服务提供者的目标项目请求路径搭配
     */
    public void test1(){

    }

    /**
     * ribbon路由的测试
       URl 如果不是简单路由的格式也不是跳转路由的格式，
       zuul就会看成一个serviceid了,
       url 格式是HTTP或HTTPS开头的就会看作是简单路由，
       格式是forward: 开头就是跳转路由了


     *http://localhost:9000/zuultest/zuulServer_test2
     *http://localhost:9000/sourcezjm/zuulServer_test2
     * 该路径根据yml配置都会转发的路径为
     * http://localhost:8082/zuulServer_test2
     */
    public void test2(){

    }

    /**
     *
     * 跳转路由的测试  （该测试无效，可能是有自定义过滤器的原因）
       转发路由 URL参数需要由forward:...构成
       http://localhost:9000/forwardZuul/张三
       该路径根据yml配置会转发到本控制器的forwardZuul_test1方法
     * 请求路径要跟网关服务提供者的目标项目请求路径搭配
     */
    public void test3(){

    }

    @GetMapping("/forwardZuul_test1/{name}")
    public String forwardZuul_test1(@PathVariable("name") String name){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        String time=simpleDateFormat.format(new Date());

        return time+":跳转路由测试:"+name;
    }
}

package com.allen.springcloud.hystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Future;

/**
 * 容错方面调用需要多刷几次，很多时候会出现缓存原因效果不准
 */
@Controller
public class HystrixControl {
    @Autowired
    private  HystrixService hystrixService;



    /**
     * springCloud整合hystrix的容错降级测试1
     * 采用代码单个函数配置法进行超时时间和请求次数的配置
     */
    //http://localhost:9000/hystrix_test1
    @GetMapping("/hystrix_test1")
    @ResponseBody
    public String hystrix_test1() {
      String ret=hystrixService.hystrixTest1("springCloud整合hystrix的容错测试1");
        return ret;
    }

    /**
     * springCloud整合hystrix的缓存测试1
     * 测试缓存需要自定义过滤器
     * 不然启动会报错 Request caching is not available. Maybe you need to initialize the HystrixRequestContext
     */
    //http://localhost:9000/hystrixCache_test1
    @GetMapping("/hystrixCache_test1")
    @ResponseBody
    public String hystrixCache_test1() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        String time=simpleDateFormat.format(new Date());
        String ret="",content="";
        /**
         * 达到缓存效果必须在同一次请求的上下文才可以实现，且方法名和参数值要一样
         * 通过控制台的输出可以发现因为缓存的原因才只打印五次的调用记录，
         * 会比想象中的多两次是因为i=150和160时分别删除了缓存，所以id=2的情况会总共打印三次
         */
        for (int i=0;i<500;i++) {
            if (i>=0&&i<100) {
                content = hystrixService.getCache(1);
            }else if (i>=100&&i<=200){
                content = hystrixService.getCache(2);
                if (i==150||i==160){
                    hystrixService.removeCache(2);
                }
            }else{
                content = hystrixService.getCache(3);
            }
            ret=ret+"--"+content;
        }
        return time+":"+ret;
    }


    /**
     * springCloud整合hystrix的请求合并测试1
     * 请求合并就是通过配置会把多次不同参数的同一个方法请求进行合并处理，达到提高响应速度
     *  为了节约宽带，有时候多次请求，方法一样，可以对参数值(值可一样或不一样)进行合并处理。
     * @return
     */
    //http://localhost:9000/hystrixCollapser_test1
    @GetMapping("/hystrixCollapser_test1")
    @ResponseBody
    public String hystrixCollapser_test1() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        String time=simpleDateFormat.format(new Date());
        String ret="",content="";
        try {
            Future<String> stringFuture1=hystrixService.restTemplateTestServer2(2018);
            Future<String> stringFuture2=hystrixService.restTemplateTestServer2(2019);
            Future<String> stringFuture3=hystrixService.restTemplateTestServer2(2020);
            Future<String> stringFuture4=hystrixService.restTemplateTestServer2(2020);
            content=stringFuture1.get();
            ret=ret+"--"+content;
            content=stringFuture2.get();
            ret=ret+"--"+content;
            content=stringFuture3.get();
            ret=ret+"--"+content;
            content=stringFuture4.get();
            ret=ret+"--"+content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time+":"+ret;
    }


    /**
     * hystrix、feign与springcloud的整合测试1
     * 在feign接口上配置对应的回退函数
     * 该请求路径是不带参数类型
     *
     * 注：yml配置文件中配置了全局的超时时间和请求次数
     */
    //http://localhost:9000/springCloudHystrixFeign_test1
    @GetMapping("/springCloudHystrixFeign_test1")
    @ResponseBody
    public String springCloudHystrixFeign_test1(){
        String content="";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        String time=simpleDateFormat.format(new Date());
        try {
            content=hystrixService.springCloudHystrixFeign_test1();
            content=time+":hystrix与springCloud的整合整合测试正常:"+content;
        } catch (Exception e) {
            e.printStackTrace();
            content=time+":hystrix与springCloud的整合整合测试正常异常!";
        }
        return content;
    }

    /**
     * hystrix、feign与springcloud的整合测试2
     * 该请求路径是带参数类型的
     */
    //http://localhost:9000/springCloudHystrixFeign_test2/张三
    @GetMapping("/springCloudHystrixFeign_test2/{name}")
    @ResponseBody
    public String springCloudHystrix_test2(@PathVariable String name){
        String content="";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        String time=simpleDateFormat.format(new Date());
        try {
            content=hystrixService.springCloudHystrixFeign_test2(name);
            content=time+":hystrix与springCloud的整合整合测试正常:"+content;
        } catch (Exception e) {
            e.printStackTrace();
            content=time+":hystrix与springCloud的整合整合测试正常异常!";
        }
        return content;
    }
}

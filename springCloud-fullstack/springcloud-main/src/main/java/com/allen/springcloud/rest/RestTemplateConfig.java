package com.allen.springcloud.rest;

import com.allen.springcloud.ribbon.RibbonDiyLoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by allen on 2019/7/10.
 */
@Configuration
public class RestTemplateConfig {
    /**
     * 跟负载均衡无关的RestTemplate
     * @return
     */
    @Bean
    public RestTemplate getBaseRestTemplate() {
        return new RestTemplate();
    }
    /**
     * 负载均衡RestTemplate
     * 新增了@LoadBalanced该支持负载均衡的注解可以用服务id调用服务提供者，
     * 如无该注解只能用具体的ip和端口号调用
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    /**
     * 获取自定义拦截器的restTemplate
     * @return
     */
    @Bean
    @RibbonDiyLoadBalanced
    public RestTemplate getDiyInterceptorRestTemplate() {
        return new RestTemplate();
    }
}

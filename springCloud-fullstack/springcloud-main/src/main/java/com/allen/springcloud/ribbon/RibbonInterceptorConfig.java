package com.allen.springcloud.ribbon;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allen on 2019/7/10.
 */
@Configuration
public class RibbonInterceptorConfig {

    /**
     * @Autowired(required = false)自动装配,声明依赖注入，
     * RestController中添加了@MyLoadBalanced注解的所有bean会添加到这个集合里面
     */
    @Autowired(required = false)
    @RibbonDiyLoadBalanced
    private List<RestTemplate> tpls=new ArrayList<RestTemplate>();

    @Bean//该bean被初始化之后声明spring 容器启动之后为所有的RestTemplate添加拦截器
    public SmartInitializingSingleton lbInitializing() {
        System.out.println("tpls.size:"+tpls.size());//打印出List<RestTemplate>集合的大小
        return new SmartInitializingSingleton() {

            public void afterSingletonsInstantiated() {
                for(RestTemplate tpl : tpls) {
                    List<ClientHttpRequestInterceptor> interceptors = tpl.getInterceptors();
                    interceptors.add(new RibbonDiyInterceptor());//添加拦截器这边很关键，不然会报错
                    tpl.setInterceptors(interceptors);
                }
            }

        };
    }
}

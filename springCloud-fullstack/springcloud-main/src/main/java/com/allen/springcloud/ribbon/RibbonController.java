package com.allen.springcloud.ribbon;

import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.client.http.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.netflix.config.ConfigurationManager.getConfigInstance;

/**
 * Created by allen on 2019/7/10.
 */
@RestController
public class RibbonController {

    @Autowired
    @Qualifier("getRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("getDiyInterceptorRestTemplate")
    private RestTemplate getDiyInterceptorRestTemplate;

    /**
     * RestClient简单的负载均衡测试1
     * 均衡轮询调用，跟springcloud自定义规则无效，在这边不起作用
     * @return
     */
    //http://localhost:9000/restClientLoadBalancing_test1
    @GetMapping("/restClientLoadBalancing_test1")
    @ResponseBody
    public String restClientLoadBalancing_test1() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        String time=simpleDateFormat.format(new Date());
        String ret="",content="";
        try {
            //如果一个服务都没有就会报错，有任何一个就会全部为这个服务路径
            getConfigInstance().setProperty("my-client.ribbon.listOfServers", "localhost:8080,localhost:8081");
            RestClient client = (RestClient) ClientFactory.getNamedClient("my-client");
            HttpRequest request = HttpRequest.newBuilder().uri("/baseRibbonServer").build();
            for(int i = 0; i < 100; i++) {
                HttpResponse response = client.executeWithLoadBalancer(request);
                content = response.getEntity(String.class);
                ret=ret+"----"+content;
            }
            return time+":RestClient简单的负载均衡测试1正常:"+ret;
        }catch (Exception e){
            e.printStackTrace();
            return time+":RestClient简单的负载均衡测试1异常";
        }
    }

    /**
     * ribbon内置规则的负载均衡测试1
     * Ribbon 内置的负载均衡规则列表：RoundRobinRule、AvailabilityFilteringRule、WeightedResponseTimeRule
     ZoneAvoidanceRule、BestAvailableRule、RandomRule、RetryRule
     如果不配置使用哪个负载均衡规则，服务会均衡轮询调用，不存在偏向的情况
     不会使用自定义的负载均衡规则
     * @return
     */
    //http://localhost:9000/ribbonInnerLoadBalancing_test1
    @GetMapping("/ribbonInnerLoadBalancing_test1")
    @ResponseBody
    public String ribbonInnerLoadBalancing_test1() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        String time=simpleDateFormat.format(new Date());
        String ret="",content="";
        try {
            BaseLoadBalancer lb = new BaseLoadBalancer();
            IRule rule = new RandomRule();//实例化ribbon内置规则
            rule.setLoadBalancer(lb);
            lb.setRule(rule);

            //至少需要有一个服务提供方地址，不然会报错
            List<Server> servers = new ArrayList<Server>();
            servers.add(new Server("localhost", 8080));
            servers.add(new Server("localhost", 8081));
            lb.addServers(servers);
            for(int i = 0; i < 100; i++) {
                Server s = lb.chooseServer(null);
                System.out.println("s:"+s);
                content = restTemplate.getForObject("http://springboot-serviceprovider/baseRibbonServer", String.class);
                ret=ret+"-----"+content;
            }
            return time+":ribbon内置规则的负载均衡测试1正常:"+ret;
        }catch (Exception e){
            e.printStackTrace();
            return "ribbon内置规则的负载均衡测试1异常";
        }
    }


    /**
     * ribbon自定义规则的负载均衡测试1
     * 会更偏向调用8080端口的服务
     * @return
     */
    //http://localhost:9000/ribbonDiyLoadBalancing_test1
    @GetMapping("/ribbonDiyLoadBalancing_test1")
    @ResponseBody
    public String ribbonDiyLoadBalancing_test1() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        String time=simpleDateFormat.format(new Date());
        String ret="",content="";
        try {
            RestTemplate tpl = restTemplate;
            BaseLoadBalancer lb = new BaseLoadBalancer();
            IRule rule = new RibbonDiyRule();//实例化自定义规则,该规则更偏向8080
            rule.setLoadBalancer(lb);
            lb.setRule(rule);

            List<Server> servers = new ArrayList<Server>();
            servers.add(new Server("localhost", 8080));
            servers.add(new Server("localhost", 8081));
            lb.addServers(servers);
            for(int i = 0; i < 100; i++) {
                Server s = lb.chooseServer(null);
                System.out.println(s);
                content = tpl.getForObject("http://springboot-serviceprovider/baseRibbonServer", String.class);
                ret=ret+"-----"+content;
            }
            return time+":ribbon自定义规则的负载均衡测试1正常:"+ret;
        }catch (Exception e){
            e.printStackTrace();
            return time+":ribbon自定义规则的负载均衡测试1异常";
        }
    }


    /**
     * ribbon自定义规则的负载均衡测试2：
     * 不实例化负载均衡器和负载均衡规则，直接采取springCloud配置的默认规则，
     * 配置类 RibbonDiyLoadBalanceClient.java
     * 会更偏向调用8080端口的服务
     * @return
     */
    //http://localhost:9000/ribbonDiyLoadBalancing_test2
    @GetMapping("/ribbonDiyLoadBalancing_test2")
    @ResponseBody
    public String ribbonDiyLoadBalancing_test2() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        String time=simpleDateFormat.format(new Date());
        String ret="",content="";
        try {
            RestTemplate tpl = restTemplate;
            for(int i = 0; i < 100; i++) {
                content = tpl.getForObject("http://springboot-serviceprovider/baseRibbonServer", String.class);
                ret=ret+"--------"+content;
            }
            return time+":ribbon自定义规则的负载均衡测试2正常:"+ret;
        }catch (Exception e){
            e.printStackTrace();
            return time+":ribbon自定义规则的负载均衡测试2异常";
        }
    }

    /**
     * 自定义拦截器的负载均衡测试，事实上负载均衡的内部原理就是起源于对请求的拦截再转发,
     * 控制层我们用服务提供方的id进行访问，拦截器会转换为具体的ip和端口号
     * 本自定义拦截器的拦截规则偏向8081端口
     * @return
     */
    //http://localhost:9000/diyInterceptoLoadBalancing_test1
    @GetMapping("/diyInterceptoLoadBalancing_test1")
    @ResponseBody
    public String diyInterceptoLoadBalancing_test1() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        String time=simpleDateFormat.format(new Date());
        String ret="",content="";
        try {
            RestTemplate tpl = getDiyInterceptorRestTemplate;
            for(int i = 0; i < 100; i++) {
                content= tpl.getForObject("http://springboot-serviceprovider/baseRibbonServer", String.class);
                ret=ret+"-----------"+content;
            }
            return time+":自定义拦截器负载均衡测试1正常:"+ret;
        }catch (Exception e){
            e.printStackTrace();
            return  time+":自定义拦截器负载均衡测试1异常";
        }
    }

}

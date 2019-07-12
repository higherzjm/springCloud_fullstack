package com.allen.springcloud.ribbon;

import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * 这边的配置会向spring容器中springboot-serviceprovider服务提供者
 * 注入默认的自定义负载均衡规则，调用会偏向8080端口
 */
@RibbonClient(name = "springboot-serviceprovider", configuration = MyRibbonConfig.class)
public class RibbonDiyLoadBalanceClient {

}

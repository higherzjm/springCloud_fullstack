package com.allen.springcloud.ribbon;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;

/**
 * 注意类名命名规则的问题，这边如果命名以Ribbon开头虽然启动没事但调用会报错
 */
public class MyRibbonConfig {
	/**
	 * 返回规则类bean实例，有了bean示例系统就会把该bean放到spring容器中，
	 * 会做为sprindCloud ribbon 默认的负载均衡规则
	 */
	@Bean
	public IRule getRule() {
		return new RibbonDiyRule();
	}

}

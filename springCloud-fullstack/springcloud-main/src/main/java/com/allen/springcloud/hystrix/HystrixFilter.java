package com.allen.springcloud.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 对每一个请求进行拦截
 * 过滤器可以初始化请求上下文
 */
@WebFilter(urlPatterns = "/*", filterName = "hystrixFilter")
public class HystrixFilter implements Filter {

	public HystrixFilter() {
		System.out.println("过滤器实例化");
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("过滤器初始化");
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("hystrixFilter过滤器");
		HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			
		} finally {
			ctx.shutdown();
		}
	}
	public void destroy() {
		
	}
}

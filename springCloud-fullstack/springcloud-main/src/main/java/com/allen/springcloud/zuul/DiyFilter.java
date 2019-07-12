package com.allen.springcloud.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * Created by allen on 2019/7/9.
 * 自定义过滤器
 */
@Component
public class DiyFilter extends ZuulFilter {
    /**
     * 设置过滤器类型
     *
     * @return String
     */
    @Override
    public String filterType() {
        //设置为前置过滤器
        return PRE_TYPE;
    }
    /**
     * 过滤器顺序：值越小，越先执行
     *
     * @return int
     */
    @Override
    public int filterOrder() {
        //不能是最先执行的
        return 4;
    }
    /**
     * 过滤是否生效
     * @return true if the run() method should be invoked. false will not invoke the run() method
     * 返回true就继续执行下面的run方法，否则不调用
     * 返回false，不会执行run方法，直接会和服务提供方连接
     */
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String url=request.getRequestURI();
        System.out.println("url:"+url);
        if (url.contains("/sourcezjm/zuulServer_test1")){
            return true;
        }else {
            return false;
        }
    }

    /**
     *
     * 为了测试的效果该业务逻辑直接返回正常并向服务提供方发送参数名称为age的参数值
     * @return Object
     */
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();

        Map<String,List<String>> map=new HashMap<String, List<String>>();
        List<String> list=new ArrayList<String>();
        list.add("2020");
        map.put("year",list);

        requestContext.setSendZuulResponse(true);
        //设置ResponseStatusCode为200才能成功和服务提供方连接,否则服务终止
        requestContext.setResponseStatusCode(HttpStatus.OK.value());
        //设置请求都参数值
        requestContext.setRequestQueryParams(map);
        return null;
    }
}

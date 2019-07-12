package com.allen.springcloud.feign;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import feign.Contract.BaseContract;
import feign.MethodMetadata;

/**
 * 自定义feign注解翻译器
 * 该合约会使用手动调用的方式
 */
public class FeignDiyContract1 extends BaseContract {
    @Override
	protected void processAnnotationOnClass(MethodMetadata data, Class<?> clz) {
		// TODO Auto-generated method stub
	}
	@Override
	protected void processAnnotationOnMethod(MethodMetadata data,
			Annotation annotation, Method method) {
		// 注解是FeignDiyInterfaceRule类型的，才处理
		if(FeignDiyInterfaceRule.class.isInstance(annotation)) {
			System.out.println("feign自定义注解翻译器");
			FeignDiyInterfaceRule myRule_feign = method.getAnnotation(FeignDiyInterfaceRule.class);
			String url = myRule_feign.url();
			String httpMethod = myRule_feign.method();
			data.template().method(httpMethod);
			data.template().append(url);
		}
	}
	@Override
	protected boolean processAnnotationsOnParameter(MethodMetadata data,
			Annotation[] annotations, int paramIndex) {
		// TODO Auto-generated method stub
		return false;
	}
}
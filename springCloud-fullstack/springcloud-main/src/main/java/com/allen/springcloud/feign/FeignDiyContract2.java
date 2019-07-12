package com.allen.springcloud.feign;

import feign.MethodMetadata;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 自定义注解翻译器
 * 该合约会使用注入spring容器的方式
 */
public class FeignDiyContract2 extends SpringMvcContract {
   public FeignDiyContract2() {
      System.out.println("实例化MyContract_feign2");
   }

   @Override
   protected void processAnnotationOnMethod(MethodMetadata data,
                                            Annotation annotation, Method method) {
      /**
       * 保留父类的翻译功能,如果不保留默认的注解翻译器会失效，且启动会报错
       */
      super.processAnnotationOnMethod(data, annotation, method);

      /**
       * 新增自定义的翻译功能
       * 注解是MyUrl类型的，才处理
       */
      if(FeignDiyInterfaceRule.class.isInstance(annotation)) {
         System.out.println("#############  这是自定义翻译器");
         FeignDiyInterfaceRule myUrl = method.getAnnotation(FeignDiyInterfaceRule.class);
         String url = myUrl.url();
         String httpMethod = myUrl.method();
         data.template().method(httpMethod);
         data.template().append(url);
      }
   }
}
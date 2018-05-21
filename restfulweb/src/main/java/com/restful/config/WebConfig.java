package com.restful.config;

import com.restful.intercept.TimeIntercept;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    @Resource
    private TimeIntercept timeIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeIntercept);
        super.addInterceptors(registry);
    }



}

package com.lzdn.manage.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.lzdn.manage.utils.auth.AuthInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 将所有/static/** 访问都映射到classpath:/static/ 目录下
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}
	//添加权限拦截器
    public void addInterceptors(InterceptorRegistry registry) {  
        registry.addInterceptor(new AuthInterceptor());  
    }  
}

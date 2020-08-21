package com.config;

import com.intercepter.JWTInterceptor;
import com.intercepter.TwoInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    //spring拦截器加载在springcontentText之前，所以这里用@Bean提前加载。否则会导致过滤器中的@AutoWired注入为空
    @Bean
    JWTInterceptor jwtInterceptor(){
        return new JWTInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //对于所有路径拦截，验证
        registry.addInterceptor(new TwoInterceptor()).addPathPatterns("/**");


        System.out.println("JWT拦截器启动");
        registry.addInterceptor(jwtInterceptor())
               .excludePathPatterns("/api/user/register").excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/imgs")
                .addPathPatterns();


    }
}

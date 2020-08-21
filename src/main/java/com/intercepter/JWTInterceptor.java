package com.intercepter;

import com.exception.CustomException;
import com.exception.CustomExceptionType;
import com.utils.JWTUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        // response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");

        //imgs/id对于get不拦截。delete拦截
        String method = request.getMethod();
        System.out.println(method);
        String requestUrl = request.getRequestURL().toString();
        System.out.println(requestUrl);
        int a=requestUrl.lastIndexOf('/');
        String b=requestUrl.substring(a-4,a);
        if(method.equals("GET")&&b.equals("imgs"))
            return true;
        


        //后台管理页面产生的token
        String token = request.getHeader("authorization");
        //判断是否过期
        if(token==null)//return false;这里应该有一个统一异常处理
            throw new CustomException(CustomExceptionType.NOT_LOGIN,"token不存在");
        Optional.ofNullable(token)
                .map(n -> {
                    try {
                        return JWTUtils.parseJWT(n);
                    } catch (Exception e) {
                        throw new CustomException(CustomExceptionType.NOT_LOGIN,"token不存在");
                    }
                });

        return true;
    }

}
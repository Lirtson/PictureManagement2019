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
public class TwoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        //response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");

        /*你总不能对所有请求404.。。*/
        /*
        String method = request.getMethod();
        System.out.println(method);
        String requestUrl = request.getRequestURL().toString();
        System.out.println(requestUrl);
        int a=requestUrl.lastIndexOf('/');
        String b=requestUrl.substring(a-4,a);
        if(method.equals("GET")&&b.equals("imgs"))
            return true;


        response.sendError(HttpServletResponse.SC_NOT_FOUND,"请求路径错误");
        */
        return true;
    }
}

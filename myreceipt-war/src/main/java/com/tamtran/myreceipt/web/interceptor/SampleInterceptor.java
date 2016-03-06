package com.tamtran.myreceipt.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * Created by Tam Tran on 11/3/2015.
 */
@Component
public class SampleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        /*System.out.println("In Pre Handle");
        List<String> headerNames = Collections.list(httpServletRequest.getHeaderNames());
        for (String headerName : headerNames) {
            System.out.println(headerName + " " + httpServletRequest.getHeader(headerName));
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        /*System.out.println("In Post Handle");
        List<String> headerNames = Collections.list(httpServletRequest.getHeaderNames());
        for (String headerName : headerNames) {
            System.out.println(headerName + " " + httpServletRequest.getHeader(headerName));
        }*/
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        /*System.out.println("In After Completion");
        List<String> headerNames = Collections.list(httpServletRequest.getHeaderNames());
        for (String headerName : headerNames) {
            System.out.println(headerName + " " + httpServletRequest.getHeader(headerName));
        }*/
    }
}

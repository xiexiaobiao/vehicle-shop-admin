package com.biao.shop.authority.conf;

import io.netty.handler.codec.http.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 经测试@WebFilter无法生效，使用FilterRegistrationBean方式替代
//@WebFilter(urlPatterns = "/*",dispatcherTypes = DispatcherType.REQUEST)
@Order(value = 1)
public class CorsFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("WebFilter init >>> ");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        logger.info("WebFilter doFilter >>> ");
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        // 解决preflight跨域问题，第一次预检请求OPTIONS请求直接返回200，
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(200);
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
            return;
        }

        String origin = request.getHeader("Origin");
        logger.info("origin >>> {}",origin);
        if (StringUtils.isNotBlank(origin)){
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,origin);
        }

        String methods = request.getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS);
        logger.info("methods >>> {}",methods);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*");

        String header = request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
        logger.info("header >>> {}",header);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"x-requested-with,content-type");
        if (StringUtils.isNotBlank(header)){
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, header);
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, header);
        }

        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        System.out.println("WebFilter destroy >>> ");
    }
}

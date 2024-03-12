//package com.example.onlinestore.Filter;
//
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebFilter;
//import java.io.IOException;
//
//@WebFilter(urlPatterns="/test/*")
//public class RequestFilter implements Filter {
//
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        CustomRequestWrapper rereadableRequestWrapper = new CustomRequestWrapper((HttpServletRequest
//                ) request);
//        chain.doFilter(rereadableRequestWrapper, response);
//    }
//
//}
//package com.example.onlinestore.intercepter;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.tomcat.util.http.fileupload.IOUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//
//@Component
//public class MyInterceptor implements HandlerInterceptor {
//
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // 요청 바디를 문자열로 변환하여 출력
//        StringBuilder requestBodyBuilder = new StringBuilder();
//        try (BufferedReader reader = request.getReader()) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                requestBodyBuilder.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String requestBody = requestBodyBuilder.toString();
//        System.out.println("Request Body: " + requestBody);
//        System.out.println("-------------------------------------------------------------");
//        return true; // 처리 흐름을 계속할지 여부를 반환합니다.
//    }
//
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        // 요청 처리 후, 뷰를 렌더링하기 전에 수행할 작업을 여기에 작성합니다.
//    }
//
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("--------------------------start------------------------------");
//        System.out.println("-------------------------------------------------------------");
//        // 요청 처리가 완료된 후에 수행할 작업을 여기에 작성합니다.
//    }
//}
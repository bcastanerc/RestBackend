package com.esliceu.backend.interceptors;

import com.esliceu.backend.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader("Authorization");
        System.out.println("METHOOD" +  request.getMethod());
        if ((request.getMethod().equals("POST") && !request.getContextPath().equals("/login") && !request.getContextPath().equals("/register"))
                || request.getMethod().equals("PUT") || request.getMethod().equals("DELETE")){
            if (header == null){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            try{
                String token = header.replace("Bearer ", "");
                String user = tokenService.getSubject(token);
                request.setAttribute("user", user);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }
        return false;
    }
}
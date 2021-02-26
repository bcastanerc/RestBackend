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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals("OPTIONS")) return true;
        String header = request.getHeader("Authorization");
        try{
            String token = header.replace("Bearer ", "");
            String user = tokenService.getSubject(token);
            request.setAttribute("user", user);
            return true;
        }catch (Exception e){
            request.setAttribute("user", null);
            return true;
        }
    }
}
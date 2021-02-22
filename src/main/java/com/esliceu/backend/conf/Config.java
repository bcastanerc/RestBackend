package com.esliceu.backend.conf;

import com.esliceu.backend.interceptors.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Config implements WebMvcConfigurer {

    @Autowired
    TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns(getInterceptorMapping())
                .excludePathPatterns(getExcludeMapping());
    }

    public List<String> getInterceptorMapping(){
        List<String> interceptorMappingList = new ArrayList<>();
        interceptorMappingList.add("/getprofile");
        interceptorMappingList.add("/profile/password");
        interceptorMappingList.add("/topics/*");
        interceptorMappingList.add("/profile");
        interceptorMappingList.add("/topics/*/replies");
        interceptorMappingList.add("/topics");
        return interceptorMappingList;
    }

    @Bean
    public List<String> getExcludeMapping(){
        List<String> excludeMapping = new ArrayList<>();
        excludeMapping.add("/categories");
        excludeMapping.add("/register");
        excludeMapping.add("/login");
        return excludeMapping;
    }
}
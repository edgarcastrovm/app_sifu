package com.sifu.core.config;

import com.sifu.core.config.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RequestInterceptor requestInterceptor;

    public WebConfig(RequestInterceptor requestInterceptor) {

        System.out.println("✅ WebConfig creado con interceptor: " + requestInterceptor);
        this.requestInterceptor = requestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor)
                .addPathPatterns("/api/v1/**");
    }

}
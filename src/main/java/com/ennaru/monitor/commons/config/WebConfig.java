package com.ennaru.monitor.commons.config;

import com.ennaru.monitor.commons.resolver.ContainerIdResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ContainerIdResolver containerIdResolver;
    public WebConfig(ContainerIdResolver containerIdResolver) {
        this.containerIdResolver = containerIdResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(containerIdResolver);
    }
}

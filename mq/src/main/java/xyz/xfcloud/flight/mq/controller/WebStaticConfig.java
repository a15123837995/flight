package xyz.xfcloud.flight.mq.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * created by Jizhi on 2019/10/29
 */
@Configuration
public class WebStaticConfig extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:META-INF/resources/static/");
        super.addResourceHandlers(registry);
    }

}

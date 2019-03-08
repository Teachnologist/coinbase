package com.triche.coinbase.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableWebMvc
@Configuration
public class resourceConfig implements WebMvcConfigurer {

       @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //    if (!registry.hasMappingForPattern("/webjars/**")) {
                registry.addResourceHandler("/style/**")
                        .addResourceLocations("/webjars/material-design-lite/1.1.0/");

           registry.addResourceHandler("/stat/**")
                   .addResourceLocations("classpath:/static/");
            }
           // registry.setOrder(1);
      //  }

}

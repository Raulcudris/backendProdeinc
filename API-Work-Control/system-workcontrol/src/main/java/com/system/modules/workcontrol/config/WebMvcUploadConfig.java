package com.system.modules.workcontrol.config;
import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcUploadConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(
            final ResourceHandlerRegistry registry
    ) {
        String uploadPath = Paths
                .get("uploads")
                .toAbsolutePath()
                .normalize()
                .toUri()
                .toString();

        registry
                .addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
    }
}
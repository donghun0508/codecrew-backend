package site.codecrew.starter.web.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer.useRequestHeader("X-API-Version");
        configurer.detectSupportedVersions(true);
        configurer.addSupportedVersions("1.0");
        configurer.setDefaultVersion("1.0");
    }
}
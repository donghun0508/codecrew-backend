package site.codecrew.starter.web.autoconfigure;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.codecrew.starter.web.argumentresolver.AuthenticatedUserPrincipalArgumentResolver;

@RequiredArgsConstructor
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final AuthenticatedUserPrincipalArgumentResolver resolver;

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer.useRequestHeader("X-API-Version");
        configurer.detectSupportedVersions(true);
        configurer.addSupportedVersions("1.0");
        configurer.setDefaultVersion("1.0");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(resolver);
    }
}
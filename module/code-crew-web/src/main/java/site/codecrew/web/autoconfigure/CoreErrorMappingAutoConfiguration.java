package site.codecrew.web.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import site.codecrew.core.http.InMemoryExceptionResolver;
import site.codecrew.core.http.StandardHttpErrorRegistry;
import site.codecrew.core.http.ExceptionResolver;

@AutoConfiguration
public class CoreErrorMappingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ExceptionResolver.class)
    public ExceptionResolver errorMapping() {
        return new InMemoryExceptionResolver(StandardHttpErrorRegistry.defaultMappings());
    }

    @Bean
    @ConditionalOnMissingBean(RestClient.class)
    public RestClient restClient() {
        return RestClient.create();
    }
}
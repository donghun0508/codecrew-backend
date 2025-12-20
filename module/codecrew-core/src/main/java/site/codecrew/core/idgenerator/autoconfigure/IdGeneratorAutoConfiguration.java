package site.codecrew.core.idgenerator.autoconfigure;


import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import site.codecrew.core.idgenerator.IdGenerator;
import site.codecrew.core.idgenerator.SnowflakeIdGenerator;

@AutoConfiguration
public class IdGeneratorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(IdGenerator.class)
    public IdGenerator idGenerator() {
        return new SnowflakeIdGenerator();
    }
}

package site.codecrew.apigateway.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.security")
public class SecurityPermitAllProperties {
    private List<String> permitAll;

    public String[] getPermitAll() {
        return permitAll.toArray(String[]::new);
    }
}
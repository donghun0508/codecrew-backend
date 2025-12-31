package site.codecrew.apigateway.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.cloud.gateway.config.GlobalCorsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import site.codecrew.apigateway.security.JwtAccessDeniedHandler;
import site.codecrew.apigateway.security.JwtAuthenticationEntryPoint;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource(GlobalCorsProperties globalCorsProperties) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        if (globalCorsProperties != null && globalCorsProperties.getCorsConfigurations() != null) {
            globalCorsProperties.getCorsConfigurations()
                .forEach(source::registerCorsConfiguration);
        }
        return source;
    }

    @Bean
    @Order(0)
    public SecurityWebFilterChain permitAllChain(ServerHttpSecurity http, SecurityPermitAllProperties properties) {
        return http
            .securityMatcher(ServerWebExchangeMatchers.pathMatchers(properties.getPermitAll()))
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(ex -> ex.anyExchange().permitAll())
            .build();
    }

    @Bean
    @Order(1)
    public SecurityWebFilterChain apiChain(
        ServerHttpSecurity http,
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
        JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        return http
            .cors(Customizer.withDefaults())
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(ex -> ex.anyExchange().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .jwt(Customizer.withDefaults())
            )
            .build();
    }

//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(
//        ServerHttpSecurity http,
//        SecurityPermitAllProperties properties,
//        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
//        JwtAccessDeniedHandler jwtAccessDeniedHandler
//    ) {
//        return http
//            .cors(Customizer.withDefaults())
//            .csrf(ServerHttpSecurity.CsrfSpec::disable)
//            .authorizeExchange(ex -> ex
//                .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .pathMatchers(properties.getPermitAll()).permitAll()
//                .anyExchange().authenticated()
//            )
//            .oauth2ResourceServer(oauth2 -> oauth2
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler)
//                .jwt(withDefaults())
//            )
//            .build();
//    }
}
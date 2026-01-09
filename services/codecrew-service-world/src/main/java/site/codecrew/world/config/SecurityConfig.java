package site.codecrew.world.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import site.codecrew.world.adapter.filter.EnterTokenAuthenticationEntryPoint;
import site.codecrew.world.adapter.filter.WorldEnterTokenAuthenticationConverter;
import site.codecrew.world.adapter.filter.WorldEnterTokenAuthenticationManager;

@Configuration(proxyBeanMethods = false)
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
        ServerHttpSecurity http,
        WorldEnterTokenAuthenticationManager authenticationManager,
        WorldEnterTokenAuthenticationConverter authenticationConverter,
        EnterTokenAuthenticationEntryPoint entryPoint,
        CorsConfigurationSource corsConfigurationSource
    ) {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(authenticationManager);
        filter.setServerAuthenticationConverter(authenticationConverter);
        filter.setRequiresAuthenticationMatcher(
            ServerWebExchangeMatchers.pathMatchers(EndpointConstants.WS_ENTRY)
        );
        filter.setAuthenticationFailureHandler((webFilterExchange, exception) ->
            entryPoint.commence(webFilterExchange.getExchange(), exception)
        );

        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .logout(ServerHttpSecurity.LogoutSpec::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            .addFilterAt(filter, SecurityWebFiltersOrder.AUTHENTICATION)
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(entryPoint)
            )
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers(EndpointConstants.AUTH_WHITELIST).permitAll()
                .pathMatchers(EndpointConstants.WS_ENTRY).authenticated()
                .anyExchange().authenticated()
            )
            .build();
    }
}
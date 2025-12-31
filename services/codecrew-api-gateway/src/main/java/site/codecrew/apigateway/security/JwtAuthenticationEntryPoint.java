package site.codecrew.apigateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    private final UnauthorizedResponseWriter unauthorizedResponseWriter;
    private final AuthExceptionLogger authExceptionLogger;

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        authExceptionLogger.logUnauthorized(exchange, ex);
        return unauthorizedResponseWriter.write(exchange, HttpStatus.UNAUTHORIZED);
    }
}
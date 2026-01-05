package site.codecrew.world.adapter.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnterTokenAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    private final UnauthorizedResponseWriter unauthorizedResponseWriter;

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        logUnauthorized(exchange, ex);
        return unauthorizedResponseWriter.write(exchange, HttpStatus.UNAUTHORIZED);
    }

    public void logUnauthorized(ServerWebExchange exchange, AuthenticationException ex) {
        String method = getMethod(exchange);
        String path = getPath(exchange);

        log.warn("Unauthorized request. method={}, path={}, exceptionType={}, message={}",
            method, path, ex.getClass().getName(), ex.getMessage(), ex);
    }

    private String getPath(ServerWebExchange exchange) {
        return exchange.getRequest().getURI().getPath();
    }

    private String getMethod(ServerWebExchange exchange) {
        return exchange.getRequest().getMethod().name();
    }

}
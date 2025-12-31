package site.codecrew.apigateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Component
public class AuthExceptionLogger {

    public void logUnauthorized(ServerWebExchange exchange, AuthenticationException ex) {
        String method = getMethod(exchange);
        String path = getPath(exchange);

        log.warn("Unauthorized request. method={}, path={}, exceptionType={}, message={}",
            method, path, ex.getClass().getName(), ex.getMessage(), ex);
    }

    public void logForbidden(ServerWebExchange exchange, AccessDeniedException ex) {
        String method = getMethod(exchange);
        String path = getPath(exchange);

        log.warn("Forbidden request. method={}, path={}, exceptionType={}, message={}",
            method, path, ex.getClass().getName(), ex.getMessage(), ex);
    }

    private String getPath(ServerWebExchange exchange) {
        return exchange.getRequest().getURI().getPath();
    }

    private String getMethod(ServerWebExchange exchange) {
        return exchange.getRequest().getMethod().name();
    }
}
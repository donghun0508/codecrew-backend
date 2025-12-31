package site.codecrew.apigateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements ServerAccessDeniedHandler {

    private final UnauthorizedResponseWriter unauthorizedResponseWriter;
    private final AuthExceptionLogger authExceptionLogger;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        authExceptionLogger.logForbidden(exchange, denied);
        return unauthorizedResponseWriter.write(exchange, HttpStatus.FORBIDDEN);
    }
}
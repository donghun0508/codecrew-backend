package site.codecrew.apigateway.filter.global;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import site.codecrew.apigateway.config.JwtProperties;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.http.ApiResponse;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class JwtAuthGlobalFilter implements GlobalFilter, Ordered {
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    private final SecretKey jwtSigningKey;
    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (isWhitelisted(path)) {
            return chain.filter(exchange);
        }

        String auth = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (auth == null || auth.isBlank()) {
            return unauthorized(exchange);
        }

        String token = extractRawToken(auth);

        try {
            Jwts.parser()
                .verifyWith(jwtSigningKey)
                .build()
                .parseSignedClaims(token);

            return chain.filter(exchange);
        } catch (JwtException | IllegalArgumentException e) {
            return unauthorized(exchange);
        }
    }

    private static String extractRawToken(String token) {
        String prefix = "Bearer ";
        return token.startsWith(prefix) ? token.substring(prefix.length()) : token;
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.getHeaders().set(HttpHeaders.CACHE_CONTROL, "no-store");
        ApiResponse<?> body = ApiResponse.error(CoreErrorCode.UNAUTHORIZED);
        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(body);
        } catch (Exception e) {
            bytes = "{\"status\":\"ERROR\"}".getBytes();
        }

        return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
    }

    @Override
    public int getOrder() {
        return -100;
    }

    private boolean isWhitelisted(String path) {
        for (String pattern : jwtProperties.getWhitelist()) {
            if (PATH_MATCHER.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }
}
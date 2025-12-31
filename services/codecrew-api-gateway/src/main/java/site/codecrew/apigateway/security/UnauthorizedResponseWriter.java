package site.codecrew.apigateway.security;

import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.http.ApiResponse;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class UnauthorizedResponseWriter {

    private static final byte[] FALLBACK_BYTES =
        "{\"status\":\"ERROR\"}".getBytes(StandardCharsets.UTF_8);

    private final ObjectMapper objectMapper;

    public Mono<Void> write(ServerWebExchange exchange, HttpStatus status) {
        var response = exchange.getResponse();

        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.getHeaders().setCacheControl("no-store");

        if (status == HttpStatus.UNAUTHORIZED) {
            response.getHeaders().set(HttpHeaders.WWW_AUTHENTICATE, "Bearer error=\"invalid_token\"");
        }

        ApiResponse<?> body = ApiResponse.error(toErrorCode(status));

        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(body);
        } catch (Exception e) {
            bytes = FALLBACK_BYTES;
        }
        log.info("bytesLen={}, bodyPreview={}", bytes.length, new String(bytes, StandardCharsets.UTF_8));
        log.info("headers={}", response.getHeaders());

        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.getHeaders().setContentLength(bytes.length);
        return response.writeAndFlushWith(Flux.just(Mono.just(buffer)));
    }

    private CoreErrorCode toErrorCode(HttpStatus status) {
        if (status == HttpStatus.UNAUTHORIZED) return CoreErrorCode.UNAUTHORIZED;
        if (status == HttpStatus.FORBIDDEN) return CoreErrorCode.FORBIDDEN;
        return CoreErrorCode.INTERNAL_ERROR;
    }
}
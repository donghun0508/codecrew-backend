package site.codecrew.apigateway.filter.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class RequestLoggingFilter
    extends AbstractGatewayFilterFactory<RequestLoggingFilter.Config> {

    public RequestLoggingFilter() {
        super(Config.class);
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            var request = exchange.getRequest();
            long startTime = System.nanoTime();

            log.info("[SCG][START] {} {}",
                request.getMethod(),
                request.getURI()
            );

            return chain.filter(exchange)
                .doOnSuccess(v -> {
                    var response = exchange.getResponse();
                    long durationMs = (System.nanoTime() - startTime) / 1_000_000;
                    log.info("[SCG][END] {} {} status={} took={}ms",
                        request.getMethod(),
                        request.getURI(),
                        response.getStatusCode(),
                        durationMs
                    );
                })
                .doOnError(e -> {
                    long durationMs = (System.nanoTime() - startTime) / 1_000_000;
                    log.error("[SCG][ERROR] {} {} took={}ms msg={}",
                        request.getMethod(),
                        request.getURI(),
                        durationMs,
                        e.toString(),
                        e
                    );
                })
                .doFinally(signalType -> {
                    var response = exchange.getResponse();
                    long durationMs = (System.nanoTime() - startTime) / 1_000_000;
                    log.info("[SCG][FINALLY] {} {} signal={} status={} took={}ms",
                        request.getMethod(),
                        request.getURI(),
                        signalType,
                        response.getStatusCode(),
                        durationMs
                    );
                });
        };
    }
}

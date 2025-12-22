package site.codecrew.apigateway.filter.global;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class RequestLoggingFilter extends AbstractGatewayFilterFactory<RequestLoggingFilter.Config> {

    private final Tracer tracer;
    private final Propagator propagator;

    public RequestLoggingFilter(Tracer tracer, Propagator propagator) {
        super(Config.class);
        this.tracer = tracer;
        this.propagator = propagator;
    }

    public static class Config { }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            var request = exchange.getRequest();
            long startTime = System.nanoTime();

            return chain.filter(exchange)
                .doFirst(() -> {
                    var span = tracer.currentSpan();
                    log.info("[SCG][START] {} {} traceId={} spanId={} remote={} headers={}",
                        request.getMethod(), request.getURI(),
                        span != null ? span.context().traceId() : null,
                        span != null ? span.context().spanId() : null,
                        request.getRemoteAddress(),
                        request.getHeaders()
                    );
                })
                .doOnError(e -> {
                    var span = tracer.currentSpan();
                    long durationMs = (System.nanoTime() - startTime) / 1_000_000;

                    Throwable root = e;
                    int depth = 0;
                    while (root.getCause() != null && root.getCause() != root && depth++ < 50) {
                        root = root.getCause();
                    }

                    StringBuilder chainStr = new StringBuilder();
                    Throwable cur = e;
                    int chainDepth = 0;
                    while (cur != null && chainDepth++ < 15) {
                        if (chainDepth > 1) chainStr.append(" -> ");
                        chainStr.append(cur.getClass().getName());
                        if (cur.getMessage() != null && !cur.getMessage().isBlank()) {
                            chainStr.append(": ").append(cur.getMessage());
                        }
                        cur = cur.getCause();
                    }

                    log.error(
                        "[SCG][ERROR] {} {} took={}ms traceId={} spanId={} " +
                            "exClass={} msg={} rootClass={} rootMsg={} causeChain={} " +
                            "routeId={} requestUrl={} attrs={}",
                        request.getMethod(), request.getURI(), durationMs,
                        span != null ? span.context().traceId() : null,
                        span != null ? span.context().spanId() : null,
                        e.getClass().getName(), e.getMessage(),
                        root.getClass().getName(), root.getMessage(),
                        chainStr.toString(),
                        exchange.getAttribute("org.springframework.cloud.gateway.support.ServerWebExchangeUtils.gatewayRoute"),
                        exchange.getAttribute("org.springframework.cloud.gateway.support.ServerWebExchangeUtils.gatewayRequestUrl"),
                        exchange.getAttributes(),
                        e
                    );
                })
                .doFinally(st -> {
                    var span = tracer.currentSpan();
                    long durationMs = (System.nanoTime() - startTime) / 1_000_000;
                    log.info("[SCG][FINALLY] {} {} signal={} took={}ms traceId={} spanId={} status={}",
                        request.getMethod(), request.getURI(), st, durationMs,
                        span != null ? span.context().traceId() : null,
                        span != null ? span.context().spanId() : null,
                        exchange.getResponse().getStatusCode()
                    );
                });
        };
    }

//        return (exchange, chain) -> Mono.defer(() -> {
//            var request = exchange.getRequest();
//            long startTime = System.nanoTime();
//
//            // 1) 현재 span이 없으면 새로 생성(게이트웨이에서 traceId 만들기)
//            Span span = tracer.currentSpan();
//            boolean created;
//            if (span == null) {
//                span = tracer.nextSpan().name("scg").start();
//                created = true;
//            } else {
//                created = false;
//            }
//
//            String traceId = span.context().traceId();
//            String spanId  = span.context().spanId();
//
//            // 2) downstream으로 trace 헤더 주입(이걸 해야 뒤 서비스가 같은 traceId를 받음)
//            final Span finalSpan = span;
//            ServerHttpRequest mutatedRequest = request.mutate()
//                .headers(headers -> injectTraceHeaders(headers, finalSpan))
//                .build();
//
//            var mutatedExchange = exchange.mutate().request(mutatedRequest).build();
//
//            log.info("[SCG][START] {} {} traceId={} spanId={}",
//                request.getMethod(), request.getURI(), traceId, spanId);
//
//            return chain.filter(mutatedExchange)
//                .doOnSuccess(v -> {
//                    long durationMs = (System.nanoTime() - startTime) / 1_000_000;
//                    log.info("[SCG][END] {} {} status={} took={}ms traceId={} spanId={}",
//                        request.getMethod(), request.getURI(),
//                        mutatedExchange.getResponse().getStatusCode(),
//                        durationMs, traceId, spanId);
//                })
//                .doOnError(e -> {
//                    long durationMs = (System.nanoTime() - startTime) / 1_000_000;
//                    log.error("[SCG][ERROR] {} {} took={}ms traceId={} spanId={} msg={}",
//                        request.getMethod(), request.getURI(),
//                        durationMs, traceId, spanId, e.toString(), e);
//                })
//                .doFinally(signalType -> {
//                    long durationMs = (System.nanoTime() - startTime) / 1_000_000;
//                    log.info("[SCG][FINALLY] {} {} signal={} status={} took={}ms traceId={} spanId={}",
//                        request.getMethod(), request.getURI(),
//                        signalType,
//                        mutatedExchange.getResponse().getStatusCode(),
//                        durationMs, traceId, spanId);
//
//                    if (created) {
//                        finalSpan.end();
//                    }
//                });
//        });
//    }

//    private void injectTraceHeaders(HttpHeaders headers, Span span) {
//        // Propagator가 설정된 포맷(W3C traceparent, B3 등)에 맞게 자동 주입
//        propagator.inject(span.context(), headers, HttpHeaders::set);
//    }
}

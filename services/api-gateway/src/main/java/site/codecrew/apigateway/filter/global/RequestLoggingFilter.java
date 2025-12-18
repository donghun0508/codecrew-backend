package site.codecrew.apigateway.filter.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class RequestLoggingFilter extends AbstractGatewayFilterFactory<RequestLoggingFilter.Config> {

    public RequestLoggingFilter() {
        super(Config.class);
    }

    public static class Config {
        // 현재 설정값 필요 없음 → 필요하면 나중에 추가 가능
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            var req = exchange.getRequest();
            log.info("[Request] {} {}", req.getMethod(), req.getURI());


            return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    var res = exchange.getResponse();
                    log.info("[Response] {} {}", res.getStatusCode(), req.getURI());
                }));
        };
    }
}

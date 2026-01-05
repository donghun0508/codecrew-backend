package site.codecrew.world.adapter.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class WorldEnterTokenAuthenticationConverter implements ServerAuthenticationConverter {

    private static final String TOKEN_PARAM = "token";

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String token = exchange.getRequest().getQueryParams().getFirst(TOKEN_PARAM);
        if (!StringUtils.hasText(token)) {
            return Mono.empty();
        }
        return Mono.just(new PreAuthenticatedAuthenticationToken(token, null));
    }
}

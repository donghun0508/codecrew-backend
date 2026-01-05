package site.codecrew.world.adapter.filter;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WorldEnterTokenAuthenticationManager implements ReactiveAuthenticationManager {

    private final WorldEnterTokenValidator tokenValidator;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String rawToken = (String) authentication.getPrincipal();
        return tokenValidator.validate(rawToken)
            .map(enterToken -> new UsernamePasswordAuthenticationToken(
                enterToken, // [중요] 나중에 세션에서 꺼내 쓸 진짜 유저 정보 (Principal)
                null,       // 자격 증명(비번)은 이미 토큰으로 증명됨
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
            ));
    }
}
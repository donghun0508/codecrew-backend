package site.codecrew.temp.adapter.filter;

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
                enterToken,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
            ));
    }
}
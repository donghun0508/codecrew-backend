package site.codecrew.world.adapter.web;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import site.codecrew.world.adapter.filter.PlayerPrincipal;
import site.codecrew.world.application.WorldEntryCommand;
import site.codecrew.world.application.WorldSessionLifecycleFacade;

@Slf4j
@RequiredArgsConstructor
@Component
public class WorldEnterWebSocketHandler implements WebSocketHandler {

    private final WorldSessionLifecycleFacade sessionLifecycleFacade;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.getHandshakeInfo().getPrincipal()
            .cast(Authentication.class)
            .map(auth -> (PlayerPrincipal) Objects.requireNonNull(auth.getPrincipal()))
            .flatMap(playerPrincipal -> sessionLifecycleFacade.onSessionEstablished(
                WorldEntryCommand.of(playerPrincipal.worldId(), playerPrincipal.playerId(), session)
            ));
    }
}
package site.codecrew.world.domain.player;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerLocalRepository playerLocalRepository;

    public Mono<Player> fetchEnterable(String identityHash) {
        return playerLocalRepository.existsById(identityHash)
            .flatMap(exists -> exists ? Mono.error(new PlayerAlreadyConnectedException(identityHash)) : Mono.empty())
            .then(playerRepository.findByIdentityHash(identityHash))
            .switchIfEmpty(Mono.error(new CoreException(CoreErrorCode.NOT_FOUND, "Player not found", identityHash)))
            .doOnNext(Player::assertEnterable)
            .flatMap(player -> playerLocalRepository.saveIfAbsent(player)
                .flatMap(success -> success ? Mono.just(player) : Mono.error(new PlayerAlreadyConnectedException(identityHash))))
            .doOnSuccess(player -> log.debug("[Player] Registered locally: {}", identityHash));
    }

    public Mono<Player> fetchLocalById(String identityHash) {
        return playerLocalRepository.findById(identityHash)
            .switchIfEmpty(Mono.error(new CoreException(CoreErrorCode.NOT_FOUND, "Player not found", identityHash)));
    }

    public Mono<Void> deleteById(String identityHash) {
        return playerLocalRepository.deleteById(identityHash)
            .doOnSuccess(v -> log.debug("[Player] Removed locally: {}", identityHash));
    }

}

package site.codecrew.world.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;
import site.codecrew.world.domain.exception.DuplicateSessionException;

@Slf4j
@RequiredArgsConstructor
@Service
class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerLocalRepository playerLocalRepository;

    // TODO: 동시성 처리 필요
    public Mono<Void> checkDuplicateLogin(String identityHash) {
        return playerLocalRepository.existsById(identityHash)
            .flatMap(exists -> exists ? Mono.error(new DuplicateSessionException(identityHash)) : Mono.empty());
    }

    public Mono<Player> fetchByIdentityHash(String identityHash) {
        return findByIdentityHash(identityHash)
            .switchIfEmpty(Mono.error(() -> new CoreException(CoreErrorCode.NOT_FOUND, "Player not found", identityHash)));
    }

    public Mono<Player> findByIdentityHash(String identityHash) {
        return playerRepository.findByIdentityHash(identityHash);
    }

    public Mono<Void> register(Player player) {
        return Mono.when(playerLocalRepository.saveIfAbsent(player));
    }

    public Mono<Void> unregister(Player playerSession) {
        return Mono.when(playerLocalRepository.delete(playerSession)).then();
    }
}

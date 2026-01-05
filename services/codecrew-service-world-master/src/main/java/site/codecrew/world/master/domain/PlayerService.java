package site.codecrew.world.master.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;
import site.codecrew.data.redis.lock.DistributedLock;

@RequiredArgsConstructor
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public Player getAvatar(PlayerId playerId) {
        return playerRepository.findByPlayerId(playerId)
            .orElseThrow(
                () -> new CoreException(CoreErrorCode.NOT_FOUND, "Avatar not found" + playerId));
    }

    public boolean existsByWorldIdAndPlayerId(Long worldId, PlayerId playerId) {
        return playerRepository.existsByWorldIdAndPlayerId(worldId, playerId);
    }

    @DistributedLock(key = "'world:' + #player.worldId + ':playerId:' + #player.playerId.value()")
    public Player create(Player player) {
        if (existsByWorldIdAndPlayerId(player.worldId(), player.getPlayerId())) {
            throw new CoreException(CoreErrorCode.CONFLICT, "Player already exists", player.getPlayerId());
        }

        return playerRepository.save(player);
    }
}

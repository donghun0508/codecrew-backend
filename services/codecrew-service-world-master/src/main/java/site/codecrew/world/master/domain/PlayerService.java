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
    public Player getAvatar(IdentityHash identityHash) {
        return playerRepository.findByIdentityHash(identityHash)
            .orElseThrow(
                () -> new CoreException(CoreErrorCode.NOT_FOUND, "Avatar not found" + identityHash));
    }

    public void deleteByIdentityHash(IdentityHash identityHash) {
        playerRepository.deleteByIdentityHash(identityHash);
    }

    public boolean existsByWorldIdAndIdentityHash(Long worldId, IdentityHash identityHash) {
        return playerRepository.existsByWorldIdAndIdentityHash(worldId, identityHash);
    }

    @DistributedLock(key = "'world:' + #player.worldId + ':playerId:' + #player.identityHash.value()")
    public Player create(Player player) {
        if (existsByWorldIdAndIdentityHash(player.worldId(), player.getIdentityHash())) {
            throw new CoreException(CoreErrorCode.CONFLICT, "Player already exists", player.getIdentityHash());
        }

        return playerRepository.save(player);
    }

    public void delete(Player player) {
        playerRepository.delete(player);
    }
}

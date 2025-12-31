package site.codecrew.world.master.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;
import site.codecrew.data.redis.lock.DistributedLock;

@RequiredArgsConstructor
@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final AvatarCacheRepository avatarCacheRepository;

    public boolean existsByWorldIdAndPlayerId(Long worldId, PlayerId playerId) {
        if (avatarCacheRepository.existsByWorldIdAndPlayerId(worldId, playerId)) {
            return true;
        }
        return avatarRepository.existsByWorldIdAndPlayerId(worldId, playerId);
    }

    @DistributedLock(key = "'world:' + #avatar.worldId + ':playerId:' + #avatar.playerId.value()")
    public Avatar create(Avatar avatar) {
        if (existsByWorldIdAndPlayerId(avatar.getWorldId(), avatar.getPlayerId())) {
            throw new CoreException(CoreErrorCode.CONFLICT, "[AvatarService.create] reason: Avatar already exists, param: " + avatar.getPlayerId());
        }

        Avatar savedAvatar = avatarRepository.save(avatar);
        avatarCacheRepository.save(savedAvatar);
        return savedAvatar;
    }

}

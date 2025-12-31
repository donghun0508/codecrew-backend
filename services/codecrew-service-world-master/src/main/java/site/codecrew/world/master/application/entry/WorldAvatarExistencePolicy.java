package site.codecrew.world.master.application.entry;

import static site.codecrew.world.master.application.exception.WorldMasterErrorCode.AVATAR_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.world.master.application.exception.WorldMasterException;
import site.codecrew.world.master.domain.AvatarService;
import site.codecrew.world.master.domain.World;
import site.codecrew.world.master.domain.WorldMember;
import site.codecrew.world.master.domain.WorldService;

@RequiredArgsConstructor
@Component
class WorldAvatarExistencePolicy implements WorldEntryPolicy {

    private final WorldService worldService;
    private final AvatarService avatarService;

    @Override
    public void validate(WorldMember worldMember) {
        World world = worldService.getByWorldCode(worldMember.world());

        if(!avatarService.existsByWorldIdAndPlayerId(world.getId(), worldMember.playerId())) {
            throw new WorldMasterException(AVATAR_NOT_FOUND, "Avatar is required. Player=" + worldMember);
        }
    }
}

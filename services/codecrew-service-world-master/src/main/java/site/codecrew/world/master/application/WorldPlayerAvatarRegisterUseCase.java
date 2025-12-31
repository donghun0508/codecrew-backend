package site.codecrew.world.master.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.world.master.domain.Avatar;
import site.codecrew.world.master.domain.AvatarService;
import site.codecrew.world.master.domain.World;
import site.codecrew.world.master.domain.WorldService;

@RequiredArgsConstructor
@Component
public class WorldPlayerAvatarRegisterUseCase {

    private final AvatarService avatarService;
    private final WorldService worldService;

    public void register(WorldPlayerAvatarRegisterCommand command) {
        World world = worldService.getByWorldCode(command.worldMember().world());
        Avatar playerAvatar = Avatar.create(world, command);
        avatarService.create(playerAvatar);
    }
}

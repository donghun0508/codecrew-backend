package site.codecrew.world.master.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.world.master.domain.Player;
import site.codecrew.world.master.domain.PlayerService;
import site.codecrew.world.master.domain.World;
import site.codecrew.world.master.domain.WorldService;

@RequiredArgsConstructor
@Component
public class WorldPlayerAvatarRegisterUseCase {

    private final PlayerService playerService;
    private final WorldService worldService;

    public void register(WorldPlayerPlayerRegisterCommand command) {
        World world = worldService.getByWorldCode(command.worldMember().world());
        Player playerPlayer = Player.create(world, command);
        playerService.create(playerPlayer);
    }
}

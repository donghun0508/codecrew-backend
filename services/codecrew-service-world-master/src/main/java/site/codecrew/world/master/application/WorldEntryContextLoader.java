package site.codecrew.world.master.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.world.master.domain.Player;
import site.codecrew.world.master.domain.PlayerService;
import site.codecrew.world.master.domain.World;
import site.codecrew.world.master.domain.WorldMember;
import site.codecrew.world.master.domain.WorldService;

@Component
@RequiredArgsConstructor
public class WorldEntryContextLoader {

    private final WorldService worldService;
    private final PlayerService playerService;

    public WorldEntryContext load(WorldMember member) {
        World world = worldService.getByWorldCode(member.world());
        Player player = playerService.getAvatar(member.identityHash());
        return new WorldEntryContext(world, player);
    }
}
package site.codecrew.world.master.application;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.world.master.domain.EnterToken;
import site.codecrew.world.master.domain.IdentityHash;
import site.codecrew.world.master.domain.WorldEnterTokenService;

@RequiredArgsConstructor
@Component
public class WorldEnterTokenIssuer {

    private final WorldEnterTokenService worldEnterTokenService;

    public EnterToken issue(WorldEntryContext entryContext) {
        return issue(entryContext.worldId(), entryContext.playerId());
    }

    public EnterToken issue(Long worldId, IdentityHash identityHash) {
        EnterToken newEnterToken = EnterToken.generate(worldId, identityHash);
        worldEnterTokenService.save(newEnterToken);
        return newEnterToken;
    }
}

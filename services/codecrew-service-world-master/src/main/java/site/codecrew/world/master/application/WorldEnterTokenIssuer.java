package site.codecrew.world.master.application;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.world.master.domain.EnterToken;
import site.codecrew.world.master.domain.WorldEnterTokenService;
import site.codecrew.world.master.domain.WorldMember;

@RequiredArgsConstructor
@Component
public class WorldEnterTokenIssuer {

    private final WorldEnterTokenService worldEnterTokenService;

    public EnterToken issue(WorldMember worldMember) {
        EnterToken newEnterToken = EnterToken.generate(worldMember);
        worldEnterTokenService.save(newEnterToken);
        return newEnterToken;
    }
}

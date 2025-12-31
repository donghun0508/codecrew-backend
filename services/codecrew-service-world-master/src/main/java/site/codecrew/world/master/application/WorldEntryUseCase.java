package site.codecrew.world.master.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.world.master.application.entry.WorldEntryPolicyChecker;
import site.codecrew.world.master.application.entry.WorldEnterResult;
import site.codecrew.world.master.domain.EnterToken;
import site.codecrew.world.master.domain.ServerNode;
import site.codecrew.world.master.domain.WorldMember;

@RequiredArgsConstructor
@Component
public class WorldEntryUseCase {

    private final WorldEntryPolicyChecker worldEntryPolicyChecker;
    private final WorldServerRoutingService worldServerRoutingService;
    private final WorldEnterTokenIssuer worldEnterTokenIssuer;

    public WorldEnterResult enter(WorldMember worldMember) {
        worldEntryPolicyChecker.checkPolicies(worldMember);
        ServerNode serverNode = worldServerRoutingService.route(worldMember.world());
        EnterToken enterToken = worldEnterTokenIssuer.issue(worldMember);
        return WorldEnterResult.of(serverNode, enterToken);
    }
}

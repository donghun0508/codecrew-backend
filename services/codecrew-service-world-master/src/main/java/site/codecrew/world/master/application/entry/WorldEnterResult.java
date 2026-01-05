package site.codecrew.world.master.application.entry;

import site.codecrew.world.master.domain.EnterToken;
import site.codecrew.world.master.domain.ServerNode;

public record WorldEnterResult(
    String host,
    String enterToken
) {

    public static WorldEnterResult of(ServerNode serverNode, EnterToken enterToken) {
        return new WorldEnterResult(
            serverNode.host(),
            enterToken.rawToken()
        );
    }
}

package site.codecrew.world.master.api.v1.response;

import site.codecrew.world.master.application.entry.WorldEnterResult;

public record WorldEnterResponse(
    String enterToken,
    ServerEndpointResponse endpoint
) {

    public static WorldEnterResponse from(WorldEnterResult result) {
        return new WorldEnterResponse(
            result.enterToken(),
            new ServerEndpointResponse(result.ip(), result.port())
        );
    }

    public record ServerEndpointResponse(
        String publicIp,
        int port
    ) {

    }
}

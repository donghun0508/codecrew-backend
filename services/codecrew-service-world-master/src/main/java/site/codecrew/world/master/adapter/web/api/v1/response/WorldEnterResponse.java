package site.codecrew.world.master.api.v1.response;

import site.codecrew.world.master.application.entry.WorldEnterResult;

public record WorldEnterResponse(
    String connectionUrl
) {

    public static WorldEnterResponse from(WorldEnterResult result) {
        return new WorldEnterResponse(
            result.host() + "/ws/world?token=" + result.enterToken()
        );
    }
}

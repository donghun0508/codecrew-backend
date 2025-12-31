package site.codecrew.world.master.application;

import site.codecrew.world.master.domain.PlayerId;
import site.codecrew.world.master.domain.WorldMember;
import site.codecrew.world.master.domain.Avatar;

public record WorldPlayerAvatarRegisterCommand(
    WorldMember worldMember,
    int avatarId,
    String nickname
) implements Avatar.CreateAvatarSpec {

    @Override
    public PlayerId playerId() {
        return worldMember.playerId();
    }
}
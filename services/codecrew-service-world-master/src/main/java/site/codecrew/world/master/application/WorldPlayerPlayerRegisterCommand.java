package site.codecrew.world.master.application;

import site.codecrew.world.master.domain.Avatar;
import site.codecrew.world.master.domain.PlayerAttribute;
import site.codecrew.world.master.domain.PlayerId;
import site.codecrew.world.master.domain.WorldMember;
import site.codecrew.world.master.domain.spec.CreatePlayerSpec;

public record WorldPlayerPlayerRegisterCommand(
    WorldMember worldMember,
    Avatar avatar,
    PlayerAttribute attribute
) implements CreatePlayerSpec {

    @Override
    public PlayerId playerId() {
        return worldMember.playerId();
    }

    @Override
    public Avatar avatar() {
        return avatar;
    }

    @Override
    public PlayerAttribute attribute() {
        return attribute;
    }
}
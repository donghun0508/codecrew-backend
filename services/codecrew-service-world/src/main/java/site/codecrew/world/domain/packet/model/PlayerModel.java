package site.codecrew.world.domain.packet.model;

import site.codecrew.world.domain.player.PlayerWithMission;

public record PlayerModel(
    String playerId,
    AvatarModel avatar,
    AttributeModel attribute,
    CollectionModel<MissionModel> missions
) {

    public static PlayerModel from(PlayerWithMission playerWithMission) {
        return new PlayerModel(
            playerWithMission.player().getIdentityHash(),
            AvatarModel.from(playerWithMission.player().getAvatar()),
            AttributeModel.from(playerWithMission.player().getAttribute()),
            CollectionModel.from(playerWithMission.missions().stream().map(MissionModel::from).toList())
        );
    }
}

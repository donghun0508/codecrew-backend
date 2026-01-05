package site.codecrew.world.master.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Avatar(
    @Column(name = "skin_id") int skinId,
    @Column(name = "hair_id") int hairId,
    @Column(name = "outfit_id") int outfitId,
    @Column(name = "accessory_id") int accessoryId
) {

    public static Avatar of(int skinId, int hairId, int outfitId, int accessoryId) {
        return new Avatar(skinId, hairId, outfitId, accessoryId);
    }
}

package site.codecrew.world.master.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Avatar(
    @Column(name = "skin_id") int skinId,
    @Column(name = "hair_id") int hairId,
    @Column(name = "hair_color_id") int hairColorId,
    @Column(name = "outfit_id") int outfitId,
    @Column(name = "accessory_id") int accessoryId
) {

    public static Avatar of(int skinId, int hairId, int hairColorId, int outfitId, int accessoryId) {
        return new Avatar(skinId, hairId, hairColorId, outfitId, accessoryId);
    }

    public void validate() {
        requireRange("hairColorId", hairColorId, 1, 7);
        requireRange("hairId", hairId, 1, 26);
        requireRange("outfitId", outfitId, 1, 33);
        requireRange("skinId", skinId, 1, 9);

        if (accessoryId != -1 && (accessoryId < 1 || accessoryId > 17)) {
            throw new IllegalArgumentException("accessoryId must be -1 or between 1 and 17 (actual: " + accessoryId + ")");
        }
    }

    private static void requireRange(String field, int value, int min, int max) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(
                field + " must be between " + min + " and " + max + " (actual: " + value + ")"
            );
        }
    }
}

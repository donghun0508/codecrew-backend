package site.codecrew.world.domain;

import org.springframework.data.relational.core.mapping.Column;

public record Avatar(
    @Column("skin_id") int skinId,
    @Column("hair_id") int hairId,
    @Column("hair_color_id") int hairColorId,
    @Column("outfit_id") int outfitId,
    @Column("accessory_id") int accessoryId
) {
}
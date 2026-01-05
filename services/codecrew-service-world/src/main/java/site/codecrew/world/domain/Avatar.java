package site.codecrew.world.domain;

import org.springframework.data.relational.core.mapping.Column;

public record Avatar(
    @Column("skin_id") int skinId,           // spring-data-relational의 Column 사용
    @Column("hair_id") int hairId,
    @Column("outfit_id") int outfitId,
    @Column("accessory_id") int accessoryId
) {
}
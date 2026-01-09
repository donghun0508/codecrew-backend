package site.codecrew.world.domain;

import org.springframework.data.relational.core.mapping.Column;

public record PlayerAttribute(
    @Column("nickname") String nickname,
    @Column("status_message") String statusMessage
) {

}

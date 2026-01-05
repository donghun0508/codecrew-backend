package site.codecrew.world.master.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record PlayerAttribute(
    @Column(name = "nickname", nullable = false) String nickname,
    @Column(name = "status_message") String statusMessage
) {

    public static PlayerAttribute of(String nickname, String statusMessage) {
        return new PlayerAttribute(nickname, statusMessage);
    }
}

package site.codecrew.youtube.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Embeddable
public record YoutubeChannelId(
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long value
) {

}

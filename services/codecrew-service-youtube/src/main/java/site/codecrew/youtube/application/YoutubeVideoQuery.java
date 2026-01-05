package site.codecrew.youtube.application;

public record YoutubeVideoQuery(
    Long lastVideoId,
    Long size,
    String searchQuery
) {

}

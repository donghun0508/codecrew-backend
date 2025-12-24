package site.codecrew.youtube.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record YoutubePlaylistItemsResponse(
    @JsonProperty("nextPageToken") String nextPageToken,
    @JsonProperty("items") List<Item> items
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Item(
        @JsonProperty("snippet") Snippet snippet,
        @JsonProperty("contentDetails") ContentDetails contentDetails
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ContentDetails(
        @JsonProperty("videoId") String videoId,
        @JsonProperty("videoPublishedAt") String videoPublishedAt
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Snippet(
        @JsonProperty("title") String title,
        @JsonProperty("description") String description,
        @JsonProperty("thumbnails") Thumbnails thumbnails
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Thumbnails(
        @JsonProperty("high") Thumbnail high,
        @JsonProperty("medium") Thumbnail medium,
        @JsonProperty("default") Thumbnail def
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Thumbnail(@JsonProperty("url") String url) {}
}
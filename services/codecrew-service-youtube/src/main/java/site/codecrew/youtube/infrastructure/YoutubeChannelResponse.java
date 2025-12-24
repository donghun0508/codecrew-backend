package site.codecrew.youtube.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record YoutubeChannelResponse(
    @JsonProperty("kind") String kind,
    @JsonProperty("etag") String etag,
    @JsonProperty("pageInfo") PageInfo pageInfo,
    @JsonProperty("items") List<Item> items
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record PageInfo(
        @JsonProperty("totalResults") Integer totalResults,
        @JsonProperty("resultsPerPage") Integer resultsPerPage
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Item(
        @JsonProperty("kind") String kind,
        @JsonProperty("etag") String etag,
        @JsonProperty("id") String id, // channelId
        @JsonProperty("snippet") Snippet snippet,
        @JsonProperty("contentDetails") ContentDetails contentDetails
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ContentDetails(
        @JsonProperty("relatedPlaylists") RelatedPlaylists relatedPlaylists
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record RelatedPlaylists(
        @JsonProperty("uploads") String uploads // uploadPlaylistId
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Snippet(
        @JsonProperty("title") String title,
        @JsonProperty("description") String description,
        @JsonProperty("customUrl") String customUrl,
        @JsonProperty("publishedAt") String publishedAt,
        @JsonProperty("thumbnails") Thumbnails thumbnails,
        @JsonProperty("localized") Localized localized,
        @JsonProperty("country") String country
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Thumbnails(
        @JsonProperty("default") Thumbnail defaultThumbnail,
        @JsonProperty("medium") Thumbnail medium,
        @JsonProperty("high") Thumbnail high
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Thumbnail(
        @JsonProperty("url") String url,
        @JsonProperty("width") Integer width,
        @JsonProperty("height") Integer height
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Localized(
        @JsonProperty("title") String title,
        @JsonProperty("description") String description
    ) {}
}
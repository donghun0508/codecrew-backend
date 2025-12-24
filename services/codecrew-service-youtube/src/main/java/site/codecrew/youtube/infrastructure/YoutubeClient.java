package site.codecrew.youtube.infrastructure;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "https://www.googleapis.com/youtube/v3", accept = "application/json")
public interface YoutubeClient {

    @GetExchange("/channels?key={apiKey}&regionCode={regionCode}")
    YoutubeChannelResponse getChannel(
        @RequestParam("part") String part,
        @RequestParam("forHandle") String forHandle
    );

    @GetExchange("/playlistItems?key={apiKey}&regionCode={regionCode}")
    YoutubePlaylistItemsResponse playlistItems(
        @RequestParam("part") String part,
        @RequestParam("playlistId") String playlistId,
        @RequestParam("maxResults") int maxResults,
        @RequestParam(value = "pageToken", required = false) String pageToken
    );
}

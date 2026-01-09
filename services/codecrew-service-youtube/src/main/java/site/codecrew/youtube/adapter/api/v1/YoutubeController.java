package site.codecrew.youtube.adapter.api.v1;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.codecrew.core.http.ApiResponse;
import site.codecrew.youtube.adapter.api.v1.request.RegisterYoutubeChannelRequest;
import site.codecrew.youtube.application.YoutubeChannelUseCase;
import site.codecrew.youtube.application.YoutubeVideoQuery;
import site.codecrew.youtube.application.YoutubeVideoQueryUseCase;
import site.codecrew.youtube.application.YoutubeVideoSyncUseCase;

@RequiredArgsConstructor
@RestController
public class YoutubeController implements YoutubeV1ApiSpec {

    private final YoutubeChannelUseCase youtubeChannelUseCase;
    private final YoutubeVideoQueryUseCase youtubeVideoQueryUseCase;
    private final YoutubeVideoSyncUseCase youtubeVideoSyncUseCase;

    @Override
    public void registerYoutubeChannel(RegisterYoutubeChannelRequest request) {
        youtubeChannelUseCase.register(request.handle());
    }

    @Override
    public void syncAll() {
        youtubeVideoSyncUseCase.syncAll();
    }

    @Override
    public ApiResponse<YoutubeVideoResponse> readAllInfiniteScroll(
        @RequestParam(value = "last_video_id", required = false) Long lastVideoId,
        @RequestParam(value = "size", required = false) Long size,
        @RequestParam(value = "search_query", required = false) String searchQuery
    ) {
        return ApiResponse.success(
            YoutubeVideoResponse.from(youtubeVideoQueryUseCase.readAllInfiniteScroll(
                new YoutubeVideoQuery(lastVideoId, size, searchQuery)
            ))
        );
    }
}

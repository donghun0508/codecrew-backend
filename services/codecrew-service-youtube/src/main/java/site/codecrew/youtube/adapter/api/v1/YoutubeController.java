package site.codecrew.youtube.adapter.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.codecrew.core.http.ApiResponse;
import site.codecrew.youtube.application.YoutubeChannelUseCase;
import site.codecrew.youtube.application.YoutubeVideoQueryUseCase;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/youtube")
public class YoutubeController implements YoutubeV1ApiSpec {

    private final YoutubeChannelUseCase youtubeChannelUseCase;
    private final YoutubeVideoQueryUseCase youtubeVideoQueryUseCase;

    @Override
    @PostMapping("/channels")
    public void registerYoutubeChannel(@RequestBody RegisterYoutubeChannelRequest request) {
        youtubeChannelUseCase.register(request.handle());
    }

    @Override
    @GetMapping("/videos")
    public ApiResponse<YoutubeVideoResponse> readAllInfiniteScroll(
        @RequestParam(value = "lastVideoId", required = false)Long lastVideoId,
        @RequestParam(value = "size", required = false) Long size
    ) {
        return ApiResponse.success(
            YoutubeVideoResponse.from(youtubeVideoQueryUseCase.readAllInfiniteScroll(lastVideoId, size))
        );
    }
}

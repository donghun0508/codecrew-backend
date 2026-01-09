package site.codecrew.youtube.adapter.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.codecrew.core.http.ApiResponse;
import site.codecrew.youtube.adapter.api.v1.request.RegisterYoutubeChannelRequest;

@Tag(name = "Youtube V1 API", description = "유튜브 채널/영상 관리 API 입니다.")
@RequestMapping("/api/v1/youtube")
public interface YoutubeV1ApiSpec {

    @Operation(
        summary = "유튜브 채널 등록",
        description = "유튜브 채널을 등록합니다."
    )
    @PostMapping("/channels")
    void registerYoutubeChannel(
        @Valid @RequestBody RegisterYoutubeChannelRequest request
    );

    @Operation(
        summary = "유튜브 채널 동기화",
        description = "유튜브 채널을 동기화합니다."
    )
    @PostMapping("/sync-all")
    void syncAll();

    @Operation(
        summary = "유튜브 영상 목록 조회(무한 스크롤)",
        description = "lastVideoId 기준으로 다음 페이지를 조회합니다. size 미지정 시 기본 20개입니다."
    )
    @GetMapping("/videos")
    ApiResponse<YoutubeVideoResponse> readAllInfiniteScroll(
        @RequestParam(value = "last_video_id", required = false) Long lastVideoId,
        @RequestParam(value = "size", required = false) Long size,
        @RequestParam(value = "search_query", required = false) String searchQuery
    );
}
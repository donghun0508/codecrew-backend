package site.codecrew.member.adapter.api.v1.request;

import jakarta.validation.constraints.NotBlank;

public record MemberNicknameUpdateRequest(
    @NotBlank String nickname
) {

}

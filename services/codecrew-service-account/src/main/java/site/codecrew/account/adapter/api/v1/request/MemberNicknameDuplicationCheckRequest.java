package site.codecrew.account.adapter.api.v1.request;

import jakarta.validation.constraints.NotBlank;

public record MemberNicknameDuplicationCheckRequest(@NotBlank String value) {

}

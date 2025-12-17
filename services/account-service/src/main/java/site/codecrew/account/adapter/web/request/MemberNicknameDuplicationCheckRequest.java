package site.codecrew.account.adapter.web.request;

import jakarta.validation.constraints.NotBlank;

public record MemberNicknameDuplicationCheckRequest(@NotBlank String value) {

}

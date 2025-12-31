package site.codecrew.member.adapter.api.v1.request;

import jakarta.validation.constraints.NotBlank;
import site.codecrew.member.application.MemberDuplicateCheckCommand;

public record MemberDuplicationCheckRequest(
    MemberDuplicateCheckCommand.Type type, @NotBlank String value) {
}

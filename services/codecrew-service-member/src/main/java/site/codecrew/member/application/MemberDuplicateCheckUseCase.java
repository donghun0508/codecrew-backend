package site.codecrew.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.member.domain.MemberService;

@RequiredArgsConstructor
@Component
public class MemberDuplicateCheckUseCase {

    private final MemberService memberService;

    public MemberDuplicateCheckResult check(MemberDuplicateCheckCommand command) {
        return new MemberDuplicateCheckResult(memberService.existsByNickname(command.value()));
    }
}

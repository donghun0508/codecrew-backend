package site.codecrew.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.member.domain.MemberService;
import site.codecrew.member.domain.Nickname;

@RequiredArgsConstructor
@Component
public class MemberDuplicateCheckUseCase {

    private final MemberService memberService;

    public MemberDuplicateCheckResult check(MemberDuplicateCheckCommand command) {
        return new MemberDuplicateCheckResult(memberService.existsByNickname(new Nickname(command.value())));
    }
}

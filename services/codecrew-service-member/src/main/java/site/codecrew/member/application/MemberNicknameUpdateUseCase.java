package site.codecrew.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.codecrew.member.domain.Member;
import site.codecrew.member.domain.MemberService;
import site.codecrew.member.domain.exception.MemberNicknameDuplicatedException;

@RequiredArgsConstructor
@Component
public class MemberNicknameUpdateUseCase {

    private final MemberService memberService;

    @Transactional
    public void updateNickname(MemberNicknameUpdateCommand command) {
        Member member = memberService.findByIdentify(command.issuerSubjectIdentity());
        if(memberService.existsByNickname(command.nickname())) {
            throw new MemberNicknameDuplicatedException(command.nickname());
        }
        member.updateNickname(command.nickname());
    }
}

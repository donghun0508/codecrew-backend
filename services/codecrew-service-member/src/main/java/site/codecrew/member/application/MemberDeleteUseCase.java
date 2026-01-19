package site.codecrew.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.member.domain.IssuerSubjectIdentity;
import site.codecrew.member.domain.Member;
import site.codecrew.member.domain.MemberService;

@RequiredArgsConstructor
@Component
public class MemberDeleteUseCase {

    private final MemberService memberService;

    public void delete(IssuerSubjectIdentity issuerSubjectIdentity) {
        Member member = memberService.findByIdentify(issuerSubjectIdentity);
        memberService.delete(member);
    }
}

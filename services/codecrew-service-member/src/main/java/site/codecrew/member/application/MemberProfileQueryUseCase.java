package site.codecrew.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.codecrew.member.domain.IssuerSubjectIdentity;
import site.codecrew.member.domain.MemberService;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class MemberProfileQueryUseCase {

    private final MemberService memberService;

    public MemberResult getMyProfile(IssuerSubjectIdentity issuerSubjectIdentity) {
        return MemberResult.from(memberService.findByIdentify(issuerSubjectIdentity));
    }
}

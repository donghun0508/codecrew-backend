package site.codecrew.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.codecrew.member.domain.IssuerSubjectIdentity;
import site.codecrew.member.domain.MemberService;

@RequiredArgsConstructor
@Component
public class MemberVerifyUseCase {

    private final MemberService memberService;

    @Transactional(readOnly = true)
    public MemberVerifyResult verify(IssuerSubjectIdentity identity) {
        boolean registered = memberService.existsByIdentity(identity);
        return new MemberVerifyResult(registered);
    }
}

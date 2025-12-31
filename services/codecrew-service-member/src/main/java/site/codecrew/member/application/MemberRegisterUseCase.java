package site.codecrew.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.codecrew.core.idgenerator.IdGenerator;
import site.codecrew.member.domain.IssuerSubjectIdentity;
import site.codecrew.member.domain.Member;
import site.codecrew.member.domain.MemberService;
import site.codecrew.member.domain.SignupAttributes;

@RequiredArgsConstructor
@Component
public class MemberRegisterUseCase {

    private final MemberService memberService;
    private final IdGenerator idGenerator;

    public void register(MemberRegisterCommand command) {
        memberService.create(Member.create(
            new IssuerSubjectIdentity(command.issuer(), command.subject()),
            new SignupAttributes(
                command.nickname(),
                command.emailAddress()
                ),
            idGenerator
        ));
    }
}

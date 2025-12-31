package site.codecrew.member.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean existsByIdentity(IssuerSubjectIdentity identity) {
        return memberRepository.existsByIssuerSubjectIdentity(identity);
    }

    public boolean existsByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public Member findByIdentify(IssuerSubjectIdentity issuerSubjectIdentity) {
        return memberRepository.findByIssuerSubjectIdentity(issuerSubjectIdentity)
            .orElseThrow(() -> new CoreException(CoreErrorCode.NOT_FOUND, "Member not found with identity", issuerSubjectIdentity));
    }

    public Member create(Member member) {
        if(existsByNickname(member.nickname())) {
            throw new CoreException(CoreErrorCode.CONFLICT, "Nickname already in use",  member.nickname());
        }
        return memberRepository.save(member);
    }
}

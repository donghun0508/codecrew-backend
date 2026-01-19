package site.codecrew.member.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;
import site.codecrew.jpa.DomainEventPublisher;
import site.codecrew.member.domain.exception.MemberNicknameDuplicatedException;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final DomainEventPublisher domainEventPublisher;

    public boolean existsByIdentity(IssuerSubjectIdentity identity) {
        return memberRepository.existsByIssuerSubjectIdentity(identity);
    }

    public boolean existsByNickname(Nickname nickname) {
        return memberRepository.existsByNicknameAndDeletedAtIsNull(nickname);
    }

    public Member findByIdentify(IssuerSubjectIdentity issuerSubjectIdentity) {
        return memberRepository.findByIssuerSubjectIdentity(issuerSubjectIdentity)
            .orElseThrow(() -> new CoreException(CoreErrorCode.NOT_FOUND, "Member not found with identity", issuerSubjectIdentity));
    }

    @Transactional
    public Member create(Member member) {
        if(existsByNickname(member.nickname())) {
            throw new MemberNicknameDuplicatedException(member.nickname());
        }
        Member savedMember = memberRepository.save(member);
        domainEventPublisher.publish(savedMember);
        return savedMember;
    }

    @Transactional
    public void delete(Member member) {
        member.delete();
        memberRepository.save(member);
        domainEventPublisher.publish(member);
    }
}

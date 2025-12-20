package site.codecrew.account.domain;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;
import site.codecrew.core.idgenerator.IdGenerator;

@RequiredArgsConstructor
@Service
@NullMarked
public class MemberService {

    private final MemberRepository memberRepository;
    private final IdGenerator idGenerator;

    public Member create(SocialProfile profile, SignupAttributes attributes) {
        findByNickname(attributes.nickname()).ifPresent((member) -> {
            throw new CoreException(CoreErrorCode.CONFLICT, "Nickname already in use: " + attributes.nickname());
        });

        return memberRepository.save(Member.createSocial(profile, attributes, idGenerator));
    }

    public Optional<Member> findByMemberProfile(SocialProfile profile) {
        return memberRepository.findBySocial(profile.socialType().name(), profile.sub());
    }

    public Optional<Member> findByPublicId(Long memberId) {
        return memberRepository.findByPublicId(memberId);
    }

    public Optional<Member> findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }
}

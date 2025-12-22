package site.codecrew.account.domain;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import site.codecrew.core.exception.CoreErrorCode;
import site.codecrew.core.exception.CoreException;
import site.codecrew.core.idgenerator.IdGenerator;

@Slf4j
@RequiredArgsConstructor
@Service
@NullMarked
public class MemberService {

    private final MemberRepository memberRepository;
    private final IdGenerator idGenerator;

    public Member create(SocialProfile profile, SignupAttributes attributes) {
        findByNickname(attributes.nickname()).ifPresent((member) -> {
            throw new CoreException(CoreErrorCode.CONFLICT,
                "Nickname already in use: " + attributes.nickname());
        });

        return memberRepository.save(Member.createSocial(profile, attributes, idGenerator));
    }

    public Optional<Member> findByMemberProfile(SocialProfile profile) {
        return memberRepository.findBySocial(profile.socialType().name(), profile.sub());
    }

    public Optional<Member> findByPublicId(Long memberId) {
        long currentTime = System.currentTimeMillis();
        Optional<Member> byPublicId = memberRepository.findByPublicId(memberId);
        log.info("Query memberId={} took {}ms", memberId, System.currentTimeMillis() - currentTime);
        return byPublicId;
    }

    public Optional<Member> findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }
}

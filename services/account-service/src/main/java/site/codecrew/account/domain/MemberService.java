package site.codecrew.account.domain;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@NullMarked
public class MemberService {

    private final MemberRepository memberRepository;

    public Member create(SocialProfile profile, SignupAttributes attributes) {
        return memberRepository.save(Member.createSocial(profile, attributes));
    }

    public Optional<Member> findByMemberProfile(SocialProfile profile) {
        return memberRepository.findBySocial(profile.socialType().name(), profile.sub());
    }
}

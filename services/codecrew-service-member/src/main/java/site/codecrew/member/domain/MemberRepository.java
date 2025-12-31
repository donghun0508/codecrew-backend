package site.codecrew.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByIssuerSubjectIdentity(IssuerSubjectIdentity identity);

    boolean existsByNickname(String nickname);

    Optional<Member> findByIssuerSubjectIdentity(IssuerSubjectIdentity issuerSubjectIdentity);
}

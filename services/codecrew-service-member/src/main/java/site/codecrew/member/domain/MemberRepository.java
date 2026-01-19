package site.codecrew.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import site.codecrew.jpa.EntityStatus;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByIssuerSubjectIdentity(IssuerSubjectIdentity identity);

    boolean existsByNicknameAndStatus(Nickname nickname, EntityStatus status);

    boolean existsByNicknameAndDeletedAtIsNull(Nickname nickname);

    Optional<Member> findByIssuerSubjectIdentity(IssuerSubjectIdentity issuerSubjectIdentity);
}

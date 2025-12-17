package site.codecrew.account.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByNickname(String nickname);

    @Query(
        value = """
        SELECT m.*
        FROM member m
        JOIN social_account sa ON sa.member_id = m.member_id
        WHERE sa.type = :type
          AND sa.sub = :sub
        """,
        nativeQuery = true
    )
    Optional<Member> findBySocial(@Param("type") String type, @Param("sub") String sub);

}
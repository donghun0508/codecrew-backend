package site.codecrew.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NullMarked;
import site.codecrew.core.idgenerator.IdGenerator;
import site.codecrew.jpa.BaseEntity;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(
    name = "member",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_member_nickname", columnNames = {"nickname"}),
        @UniqueConstraint(name = "uk_member_issuer_subject", columnNames = {"issuer", "subject"}),
        @UniqueConstraint(name = "uk_member_email", columnNames = {"email"})
    }
)
@NullMarked
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    private IssuerSubjectIdentity issuerSubjectIdentity;

    @Column(name = "public_id", nullable = false, length = 26)
    private long publicId;

    @Embedded
    private Email email;

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    public static Member create(IssuerSubjectIdentity issuerSubjectIdentity, SignupAttributes signupAttributes, IdGenerator idGenerator) {
        Member member = new Member();
        member.issuerSubjectIdentity = issuerSubjectIdentity;
        member.publicId = idGenerator.nextId();
        member.email = Email.of(signupAttributes.emailAddress());
        member.nickname = signupAttributes.nickname();
        return member;
    }

    public Long publicId() {
        return publicId;
    }

    public String emailAddress() {
        return email.address();
    }

    public String nickname() {
        return nickname;
    }
}

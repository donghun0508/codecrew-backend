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
import site.codecrew.jpa.AggregateRoot;
import site.codecrew.jpa.EntityStatus;
import site.codecrew.member.domain.MemberEvent.MemberDeletedEvent;

@Getter
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
public class Member extends AggregateRoot {

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

    @Embedded
    private Nickname nickname;

    public static Member create(IssuerSubjectIdentity issuerSubjectIdentity,
        SignupAttributes signupAttributes, IdGenerator idGenerator) {
        Member member = new Member();
        member.issuerSubjectIdentity = issuerSubjectIdentity;
        member.publicId = idGenerator.nextId();
        member.email = Email.of(signupAttributes.emailAddress());
        member.nickname = new Nickname(signupAttributes.nickname());
        return member;
    }

    @Override
    public void delete() {
        if (getStatus() == EntityStatus.DELETED) {
            return;
        }
        super.delete();

        long timestamp = System.currentTimeMillis();
        this.nickname = this.nickname.markDeleted(timestamp);
        this.email = this.email.markDeleted(timestamp);
        IssuerSubjectIdentity before = this.issuerSubjectIdentity;
        this.issuerSubjectIdentity = this.issuerSubjectIdentity.markDeleted(timestamp);
        this.registerEvent(new MemberDeletedEvent(this.id, before));
    }

    public void updateNickname(Nickname nickname) {
        this.nickname = nickname;
    }

    public Long publicId() {
        return publicId;
    }

    public String emailAddress() {
        return email.address();
    }

    public Nickname nickname() {
        return nickname;
    }
}

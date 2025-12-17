package site.codecrew.account.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NullMarked;
import site.codecrew.jpa.BaseEntity;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(
    name = "member",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_member_email", columnNames = {"email"}),
        @UniqueConstraint(name = "uk_member_nickname", columnNames = {"nickname"})
    }
)
@NullMarked
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<SocialAccount> socialAccounts = new ArrayList<>();

    public static Member createSocial(SocialProfile profile, SignupAttributes attributes) {
        Member member = new Member();
        member.email = new Email(profile.email());
        member.name = profile.name();
        member.nickname = attributes.nickname();
        member.profileImageUrl = profile.picture();
        member.addSocialAccount(profile.socialType(), profile.sub());
        return member;
    }

    private void addSocialAccount(SocialType socialType, String sub) {
        SocialAccount account = new SocialAccount(this, socialType, sub);
        this.socialAccounts.add(account);
    }

    public Long id() {
        return id;
    }

    public String email() {
        return email.address();
    }

    public String name() {
        return name;
    }

    public String nickname() {
        return nickname;
    }

    public String profileImageUrl() {
        return profileImageUrl;
    }
}

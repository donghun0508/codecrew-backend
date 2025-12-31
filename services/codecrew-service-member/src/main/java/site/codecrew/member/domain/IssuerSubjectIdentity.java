package site.codecrew.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class IssuerSubjectIdentity implements Serializable {

    @Column(name = "issuer", nullable = false, length = 255)
    private String issuer;

    @Column(name = "subject", nullable = false, length = 255)
    private String subject;

    public IssuerSubjectIdentity(String issuer, String subject) {
        this.issuer = issuer;
        this.subject = subject;
    }
}
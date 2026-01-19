package site.codecrew.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

    public IssuerSubjectIdentity markDeleted(long timestamp) {
        String suffix = "-del-" + timestamp;
        String newSubject = safeAppend(this.subject, suffix);

        String newIssuer = safeAppend(this.issuer, suffix);

        return new IssuerSubjectIdentity(newIssuer, newSubject);
    }

    private String safeAppend(String original, String suffix) {
        String appended = original + suffix;
        if (appended.length() > 255) {
            return original.substring(0, 255 - suffix.length()) + suffix;
        }
        return appended;
    }
}
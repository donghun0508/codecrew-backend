package site.codecrew.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import site.codecrew.core.domain.DomainEvent;

public class MemberEvent {

    @Getter
    @SuperBuilder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberDeletedEvent extends DomainEvent {

        private IssuerSubjectIdentity issuerSubjectIdentity;

        public MemberDeletedEvent(Long id, IssuerSubjectIdentity issuerSubjectIdentity) {
            super("member", String.valueOf(id));
            this.issuerSubjectIdentity = issuerSubjectIdentity;
        }
    }

}

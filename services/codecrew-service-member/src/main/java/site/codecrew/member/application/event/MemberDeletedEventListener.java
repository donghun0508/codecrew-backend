package site.codecrew.member.application.event;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import site.codecrew.member.application.MemberResourceDeleteUseCase;
import site.codecrew.member.domain.MemberEvent.MemberDeletedEvent;

@RequiredArgsConstructor
@Component
public class MemberDeletedEventListener {

    private final MemberResourceDeleteUseCase memberResourceDeleteUseCase;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onDeletedEvent(MemberDeletedEvent event) {
        memberResourceDeleteUseCase.cleanup(event.getIssuerSubjectIdentity());
    }
}

package site.codecrew.world.master.application.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.codecrew.core.domain.DomainEvent;
import site.codecrew.jpa.event.inbox.AbstractEventHandler;
import site.codecrew.world.master.application.event.DeletePlayerOnMemberDeletedHandler.MemberDeletedEvent;
import site.codecrew.world.master.domain.IdentityHash;
import site.codecrew.world.master.domain.MissionService;
import site.codecrew.world.master.domain.Player;
import site.codecrew.world.master.domain.PlayerService;

@Component
public class DeletePlayerOnMemberDeletedHandler extends AbstractEventHandler<MemberDeletedEvent> {

    private final PlayerService playerService;
    private final MissionService missionService;

    public DeletePlayerOnMemberDeletedHandler(PlayerService playerService, MissionService missionService) {
        super("MemberDeletedEvent", MemberDeletedEvent.class);
        this.playerService = playerService;
        this.missionService = missionService;
    }

    @Override
    @Transactional
    public void handle(MemberDeletedEvent event) {
        Player player = playerService.getAvatar(event.identityHash());
        playerService.delete(player);
        missionService.deleteByPlayerId(player.getId());
    }

    @Getter
    @NoArgsConstructor
    public static class MemberDeletedEvent extends DomainEvent {

        private IssuerSubjectIdentity issuerSubjectIdentity;

        public IdentityHash identityHash() {
            return IdentityHash.from(issuerSubjectIdentity.getIssuer(), issuerSubjectIdentity.getSubject());
        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class IssuerSubjectIdentity {
            private String issuer;
            private String subject;
        }
    }
}
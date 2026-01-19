package site.codecrew.member.application;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.stereotype.Component;
import site.codecrew.member.config.KeycloakAdminProperties;
import site.codecrew.member.domain.IssuerSubjectIdentity;

@Slf4j
@RequiredArgsConstructor
@Component
public class MemberResourceDeleteUseCase {

    private final UsersResource usersResource;
    // 주입받을 때 프로퍼티도 같이 받아서 확인해봐
    private final KeycloakAdminProperties properties;

    public void cleanup(IssuerSubjectIdentity issuerSubjectIdentity) {
        String userId = issuerSubjectIdentity.getSubject();
        String targetRealm = properties.getCodecrew().getRealm();

        // [디버깅] 이 로그가 "test-realm" 처럼 정확한지 확인해!
        log.debug(">>> Target Realm: {}", targetRealm);
        log.debug(">>> Deleting User ID: {}", userId);


        try {
            usersResource.get(userId).remove();
        } catch (NotFoundException e) {
            log.error(">>> 404 발생! {} 영역에는 {} 유저가 없음.", targetRealm, userId);
        }
    }
}

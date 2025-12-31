package site.codecrew.social.adapter.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import site.codecrew.social.domain.Provider;

@RequestMapping(value = "/api/v1/oidc", version = "1.0")
@Tag(name = "OIDC UserInfo Proxy V1 API", description = "OIDC 사용자 정보 프록시 API 입니다.")
public interface OidcUserInfoProxyV1ApiSpec {

    @Operation
    @GetMapping(value = "/{provider}/userinfo", produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Object> getUserInfo(
        @PathVariable("provider") Provider provider,
        @RequestHeader(value = "Authorization", required = true) String authorization
    );

}

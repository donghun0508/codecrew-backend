package site.codecrew.starter.web.argumentresolver;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthenticatedUserPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedUserPrincipal.class)
            && parameter.getParameterType().equals(AuthenticatedUser.class);
    }

    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) {
        String issuer = webRequest.getHeader("X-Issuer");
        String subject = webRequest.getHeader("X-Subject");

        String usernameEnc = webRequest.getHeader("X-Username");
        String username = usernameEnc == null ? null : URLDecoder.decode(usernameEnc, StandardCharsets.UTF_8);

        String email = webRequest.getHeader("X-Email");

        return new AuthenticatedUser(issuer, subject, username, email);
    }
}

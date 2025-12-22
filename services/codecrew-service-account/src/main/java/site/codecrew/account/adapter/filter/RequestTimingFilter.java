package site.codecrew.account.adapter.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
// @Component
public class RequestTimingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        if(Objects.equals(request.getRequestURI(), "/actuator/prometheus")) {
            filterChain.doFilter(request, response);
        } else {
            log.info("DOWNSTREAM START {} {}{} from {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString() != null ? "?" + request.getQueryString() : "",
                request.getRemoteAddr()
            );

            try {
                filterChain.doFilter(request, response);
            } finally {
                long duration = System.currentTimeMillis() - startTime;
                log.info("DOWNSTREAM END {} {} took {} ms (status={})",
                    request.getMethod(),
                    request.getRequestURI(),
                    duration,
                    response.getStatus()
                );
            }
        }
    }
}

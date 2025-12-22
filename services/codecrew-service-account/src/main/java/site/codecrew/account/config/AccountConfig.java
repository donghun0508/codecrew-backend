package site.codecrew.account.config;

import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
// @Configuration
public class AccountConfig {

    private static final Set<String> EXCLUDED_PATHS = Set.of(
        "/actuator/health",
        "/actuator/prometheus",
        "/actuator/metrics",
        "/favicon.ico"
    );

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> factory.addEngineValves(new ValveBase() {
            @Override
            public void invoke(Request request, Response response) throws IOException, ServletException {
                String uri = request.getRequestURI();

                if (shouldSkipLogging(uri)) {
                    getNext().invoke(request, response);
                    return;
                }

                long start = System.currentTimeMillis();
                log.info(">>> [TOMCAT IN] {}", uri);

                try {
                    getNext().invoke(request, response);
                } finally {
                    long took = System.currentTimeMillis() - start;

                    try {
                        response.flushBuffer(); // 실제 write/flush 시점에서 에러(끊김/RESET 등) 확인
                        log.info("<<< [TOMCAT OUT] status={} took={}ms flushed=true committed={}",
                            response.getStatus(), took, response.isCommitted());
                    } catch (IOException ex) {
                        log.error("<<< [TOMCAT OUT] status={} took={}ms flushed=false committed={} exClass={} msg={}",
                            response.getStatus(), took, response.isCommitted(),
                            ex.getClass().getName(), ex.getMessage(), ex);
                    }
                }
            }

            private boolean shouldSkipLogging(String uri) {
                return EXCLUDED_PATHS.contains(uri) || uri.startsWith("/actuator/");
            }
        });
    }


}

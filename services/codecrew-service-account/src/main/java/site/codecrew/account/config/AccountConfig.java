package site.codecrew.account.config;

import jakarta.servlet.ServletException;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AccountConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> {
            factory.addEngineValves(new ValveBase() {
                @Override
                public void invoke(Request request, Response response) throws IOException, ServletException {
                    long start = System.currentTimeMillis();

                    log.info(">>> [TOMCAT IN] {}", request.getRequestURI());

                    try {
                        getNext().invoke(request, response);
                    } finally {
                        log.info("<<< [TOMCAT OUT] {} in {}ms",
                            response.getStatus(),
                            System.currentTimeMillis() - start
                        );
                    }
                }
            });
        };
    }

}

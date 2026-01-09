package site.codecrew.temp.config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("prod")
@EnableScheduling
@Component
public class AgonesSdkLifecycle {

    private final HttpClient httpClient;
    private final String baseUrl;
    private final AtomicBoolean readySent = new AtomicBoolean(false);

    public AgonesSdkLifecycle() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(1))
                .build();

        this.baseUrl = "http://localhost:9358";
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        try {
            post("/ready");
            readySent.set(true);
            log.info("Agones SDK Ready sent (baseUrl={})", baseUrl);
        } catch (Exception e) {
            log.error("Agones SDK Ready failed (baseUrl={})", baseUrl, e);
        }
    }

    @Scheduled(initialDelayString = "PT5S", fixedDelayString = "PT5S")
    public void sendHealthPing() {
        if (!readySent.get()) {
            return;
        }

        try {
            post("/health");
        } catch (Exception e) {
            log.warn("Agones SDK Health failed (baseUrl={})", baseUrl, e);
        }
    }

    private void post(String path) throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .timeout(Duration.ofSeconds(1))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{}"))
                .build();

        HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
        int code = res.statusCode();
        if (code / 100 != 2) {
            throw new IllegalStateException("HTTP " + code + " body=" + res.body());
        }
    }
}
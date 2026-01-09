package site.codecrew.world.domain;

public class SessionRouting {
    private final String sessionId;
    private long routingKey;

    public SessionRouting(String sessionId, long routingKey) {
        this.sessionId = sessionId;
        this.routingKey = routingKey;
    }

    public String sessionId() {
        return sessionId;
    }

    public long routingKey() {
        return routingKey;
    }
}

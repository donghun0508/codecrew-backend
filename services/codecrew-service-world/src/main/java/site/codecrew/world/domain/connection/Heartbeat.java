package site.codecrew.world.domain.connection;


import lombok.Getter;

@Getter
final class Heartbeat {
    private volatile long lastHeartbeat = System.currentTimeMillis();

    public void updateHeartbeat() {
        this.lastHeartbeat = System.currentTimeMillis();
    }

    public long getLastHeartbeat() {
        return this.lastHeartbeat;
    }
}

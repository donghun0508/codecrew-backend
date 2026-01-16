package site.codecrew.world.domain.connection;


import lombok.Getter;
import site.codecrew.core.domain.DomainEvent;

public class ConnectionEvent {

    @Getter
    public static class ConnectionCreatedEvent extends DomainEvent {
        private final Connection connection;

        protected ConnectionCreatedEvent(Connection connection) {
            this.connection = connection;
        }
    }

    @Getter
    public static class ConnectionClosedEvent extends DomainEvent {
        private final Connection connection;

        public ConnectionClosedEvent(Connection connection) {
            this.connection = connection;
        }
    }
}

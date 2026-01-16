package site.codecrew.world.domain.player;

import java.util.function.Consumer;

public enum MissionStatus {

    PENDING(Mission::pending),
    PROCESSING(Mission::start),
    COMPLETED(Mission::complete);

    private final Consumer<Mission> statusOperation;

    MissionStatus(Consumer<Mission> statusOperation) {
        this.statusOperation = statusOperation;
    }

    public void apply(Mission mission) {
        this.statusOperation.accept(mission);
    }
}
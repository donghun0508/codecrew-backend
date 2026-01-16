package site.codecrew.world.domain.packet.transmission;

import java.util.Set;

public interface TransmissionMode {
    default Set<TransmissionOption> options() {
        return Set.of();
    }
}

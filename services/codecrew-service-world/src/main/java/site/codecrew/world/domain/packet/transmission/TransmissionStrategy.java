package site.codecrew.world.domain.packet.transmission;

public interface TransmissionStrategy {
    boolean supports(TransmissionMode mode);
}

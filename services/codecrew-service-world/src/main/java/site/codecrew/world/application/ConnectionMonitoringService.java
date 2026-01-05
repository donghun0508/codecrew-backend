package site.codecrew.world.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.codecrew.world.domain.HeartbeatService;

@RequiredArgsConstructor
@Service
public class ConnectionMonitoringService {

    private final HeartbeatService heartbeatService;

}

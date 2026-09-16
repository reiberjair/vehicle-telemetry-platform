package io.github.reiberjair.telemetry;

import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.Clock;



@Service
public class TelemetryService {
    private volatile TelemetryResponse latestSample;
    private  final Clock clock;

    public TelemetryService(Clock clock) {
        this.clock = clock;
    }

    public TelemetryResponse save(TelemetryRequest sample){
        TelemetryResponse received = new TelemetryResponse(
                sample.sequence(),
                sample.rpm(),
                Instant.now(clock)
        );
        latestSample = received;
        return received;
    }
    public TelemetryResponse getLatest(){
        return latestSample;
    }
}

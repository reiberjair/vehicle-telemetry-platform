package io.github.reiberjair.telemetry;

import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.Clock;
import java.time.Duration;



@Service
public class TelemetryService {
    private volatile TelemetryResponse latestSample;
    private  final Clock clock;
    private static final Duration STALE_TIMEOUT = Duration.ofSeconds(2);

    public TelemetryService(Clock clock) {
        this.clock = clock;
    }

    public TelemetryResponse save(TelemetryRequest sample){
        TelemetryResponse received = new TelemetryResponse(
                sample.sequence(),
                sample.rpm(),
                Instant.now(clock),
                false
        );
        latestSample = received;
        return received;
    }
    public TelemetryResponse getLatest(){

            TelemetryResponse sample = latestSample;

            if (sample == null) {
                return null;
            }

            Duration age = Duration.between(
                    sample.receivedAt(),
                    Instant.now(clock)
            );

            boolean stale = age.compareTo(STALE_TIMEOUT) >= 0;

            return new TelemetryResponse(
                    sample.sequence(),
                    sample.rpm(),
                    sample.receivedAt(),
                    stale
            );

    }
}

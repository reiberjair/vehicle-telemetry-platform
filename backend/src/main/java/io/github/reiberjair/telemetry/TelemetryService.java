package io.github.reiberjair.telemetry;

import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.Clock;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;


@Service
public class TelemetryService {
    private volatile TelemetryResponse latestSample;
    private  final Clock clock;
    private final Duration staleTimeout;

    public TelemetryService(Clock clock, @Value("${telemetry.stale-timeout}") Duration staleTimeout) {
        if (staleTimeout.isZero() || staleTimeout.isNegative()) {
            throw new IllegalArgumentException(
                    "telemetry.stale-timeout must be positive");

        }
        this.clock = clock;
        this.staleTimeout = staleTimeout;
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

        boolean stale = age.compareTo(staleTimeout) >= 0;

            return new TelemetryResponse(
                    sample.sequence(),
                    sample.rpm(),
                    sample.receivedAt(),
                    stale
            );

    }
}

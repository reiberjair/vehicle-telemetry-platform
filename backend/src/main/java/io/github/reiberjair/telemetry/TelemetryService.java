package io.github.reiberjair.telemetry;

import org.springframework.stereotype.Service;

@Service
public class TelemetryService {
    private volatile TelemetryRequest latestSample;

    public void save(TelemetryRequest sample) {
        latestSample = sample;
    }

    public TelemetryRequest getLatest() {return latestSample;}
}

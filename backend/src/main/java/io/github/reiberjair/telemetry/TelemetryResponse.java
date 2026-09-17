package io.github.reiberjair.telemetry;

import java.time.Instant;

public record TelemetryResponse(
        Integer sequence,
        Integer rpm,
        Instant receivedAt,
        boolean stale
) {

}

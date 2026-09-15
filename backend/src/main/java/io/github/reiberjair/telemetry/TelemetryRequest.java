package io.github.reiberjair.telemetry;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TelemetryRequest(
        @NotNull @PositiveOrZero Integer sequence,
        @NotNull @PositiveOrZero Integer rpm
) {
}

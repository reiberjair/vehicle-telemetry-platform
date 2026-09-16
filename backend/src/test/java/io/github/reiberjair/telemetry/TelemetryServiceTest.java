package io.github.reiberjair.telemetry;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;


public class TelemetryServiceTest {
    private final Instant fixedTime =
            Instant.parse("2026-09-16T12:00:00Z");

    private final Clock clock =
            Clock.fixed(fixedTime, ZoneOffset.UTC);

        @Test
        void shouldHaveNoSampleInitially() {
            TelemetryService telemetryService = new TelemetryService(clock);

            assertNull(telemetryService.getLatest());
        }
        @Test
        void shouldStoreSample(){
            TelemetryService service = new TelemetryService(clock);
            TelemetryRequest request = new TelemetryRequest(1, 1500);

            TelemetryResponse saved = service.save(request);
            assertEquals(request.sequence(), saved.sequence());
            assertEquals(request.rpm(), saved.rpm());
            assertEquals(fixedTime, saved.receivedAt());
            assertEquals(saved, service.getLatest());
        }

        @Test
        void shouldReplacePreviousSample(){
            TelemetryService service = new TelemetryService(clock);
            service.save(new TelemetryRequest(1, 1500));

            TelemetryResponse saved = service.save(new TelemetryRequest(2, 2300));
            assertEquals(2, saved.sequence());
            assertEquals(2300, saved.rpm());
            assertEquals(saved, service.getLatest());
        }
}

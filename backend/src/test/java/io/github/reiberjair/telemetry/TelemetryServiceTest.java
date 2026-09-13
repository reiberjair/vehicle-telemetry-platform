package io.github.reiberjair.telemetry;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TelemetryServiceTest {

        @Test
        void shouldHaveNoSampleInitially() {
            TelemetryService telemetryService = new TelemetryService();

            assertNull(telemetryService.getLatest());
        }
        @Test
        void shouldStoreSample(){
            TelemetryService service = new TelemetryService();
            TelemetryRequest sample = new TelemetryRequest(1,1500);

            service.save(sample);
            assertEquals(sample, service.getLatest());
        }

        @Test
        void shouldReplacePreviousSample(){
            TelemetryService service = new TelemetryService();
            service.save(new TelemetryRequest(1,1500));
            TelemetryRequest next = new TelemetryRequest(2,2300);
            service.save(next);
            assertEquals(next, service.getLatest());
        }
}

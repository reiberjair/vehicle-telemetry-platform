package io.github.reiberjair.telemetry;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TelemetryController {

    private final TelemetryService telemetryService;

    public TelemetryController(TelemetryService telemetryService) {
        this.telemetryService = telemetryService;
    }


    @PostMapping("/api/telemetry")
    public TelemetryRequest receiveTelemetry(
            @RequestBody TelemetryRequest sample) {
        telemetryService.save(sample);
        return sample;
    }
    @GetMapping("/api/telemetry/latest")
    public ResponseEntity<TelemetryRequest> getLatestTelemetry() {
        TelemetryRequest latest = telemetryService.getLatest();

        if (latest == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(latest);
    }
}

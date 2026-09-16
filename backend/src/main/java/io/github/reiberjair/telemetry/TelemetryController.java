package io.github.reiberjair.telemetry;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.validation.Valid;


@RestController
public class TelemetryController {

    private final TelemetryService telemetryService;

    public TelemetryController(TelemetryService telemetryService) {
        this.telemetryService = telemetryService;
    }


    @PostMapping("/api/telemetry")
    public TelemetryResponse receiveTelemetry(
            @Valid @RequestBody TelemetryRequest sample) {

        return telemetryService.save(sample);

    }
    @GetMapping("/api/telemetry/latest")
    public ResponseEntity<TelemetryResponse> getLatestTelemetry() {
        TelemetryResponse latest = telemetryService.getLatest();

        if (latest == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(latest);
    }
}

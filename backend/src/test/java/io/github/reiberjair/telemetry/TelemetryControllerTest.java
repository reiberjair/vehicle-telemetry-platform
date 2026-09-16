package io.github.reiberjair.telemetry;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;

@WebMvcTest(TelemetryController.class)
@Import({TelemetryService.class, TimeConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TelemetryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnNoContentWhenNoSampleExists() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.get("/api/telemetry/latest"))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    @Test
    void shouldReturnLatestSampleAfterPost() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/telemetry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"sequence": 1, "rpm": 1500}
                                """
                ))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/telemetry/latest"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sequence").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rpm").value(1500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.receivedAt").isNotEmpty());

    }

    @Test
    void shouldRejectNegativeRpmWithoutReplacingLatestSample() throws Exception{
        //Preparar: guardar un muestra valida
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/telemetry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"sequence": 10, "rpm": 1500}
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk());
        //Actuar: Intentar guardar una muestra invalida
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/telemetry")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {"sequence": 11, "rpm": -100}
                                """
                        ))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        //COmprobar: la muestra anterior debe seguir intacta
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/telemetry/latest"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.sequence").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rpm").value(1500));


    }
    @Test
    void shouldRejectSampleWithoutRpm() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/telemetry")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"sequence": 1}
                                """))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void ShouldAcceptZeroRpm() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/telemetry")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                {"sequence": 0, "rpm": 0}
                """))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }


}

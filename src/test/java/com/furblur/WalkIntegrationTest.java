package com.furblur;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WalkIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void fullFlow_createAndFetchWalk() throws Exception {
    	
        Walk newWalk = new Walk();
        newWalk.setDate(LocalDate.now());
        newWalk.setStartTime(LocalDateTime.now());
        newWalk.setEndTime(LocalDateTime.now().plusMinutes(45));
        newWalk.setDistanceKm(3.5);
		newWalk.setDurationFormatted("00:45:00");
		newWalk.setAveragePace(9.8);
		newWalk.setEnergyLevel("Medium");
		newWalk.setHappiness("Very Happy");
		newWalk.setBehaviour("Good");

        mockMvc.perform(post("/walks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newWalk)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.date").value(newWalk.getDate().toString()))
                .andExpect(jsonPath("$.distanceKm").value(newWalk.getDistanceKm()))
    			.andExpect(jsonPath("$.durationFormatted").value(newWalk.getDurationFormatted()))
    			.andExpect(jsonPath("$.averagePace").value(newWalk.getAveragePace()))
    			.andExpect(jsonPath("$.energyLevel").value(newWalk.getEnergyLevel()))
    			.andExpect(jsonPath("$.happiness").value(newWalk.getHappiness()))
    			.andExpect(jsonPath("$.behaviour").value(newWalk.getBehaviour()));

        mockMvc.perform(get("/walks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].date").value(newWalk.getDate().toString()))
                .andExpect(jsonPath("$[0].distanceKm").value(3.5))
    			.andExpect(jsonPath("$[0].durationFormatted").value("00:45:00"))
    			.andExpect(jsonPath("$[0].averagePace").value(9.8))
    			.andExpect(jsonPath("$[0].energyLevel").value("Medium"))
    			.andExpect(jsonPath("$[0].happiness").value("Very Happy"))
    			.andExpect(jsonPath("$[0].behaviour").value("Good"));
    }
}

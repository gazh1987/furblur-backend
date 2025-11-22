package com.furblur;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalkController.class)
@Import(WalkController.class)
public class WalkControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private WalkRepository walkRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void getAllWalks_returnListOfWalks() throws Exception {
		Walk walk1 = new Walk();
		walk1.setId(1L);
		walk1.setDate(LocalDate.now());
		walk1.setStartTime(LocalDateTime.now().minusMinutes(30));
		walk1.setEndTime(LocalDateTime.now());
		walk1.setDistanceKm(3.2);
		walk1.setDurationFormatted("00:30:00");
		walk1.setAveragePace(10.4);
		walk1.setEnergyLevel("High");
		walk1.setHappiness("Happy");
		walk1.setBehaviour("Calm");
		
		Walk walk2 = new Walk();
        walk2.setId(2L);
        walk2.setDate(LocalDate.now().minusDays(1));
        walk2.setStartTime(LocalDateTime.now().minusHours(2));
        walk2.setEndTime(LocalDateTime.now().minusHours(1));
        walk2.setDistanceKm(2.1);
		walk2.setDurationFormatted("01:00:00");
		walk2.setAveragePace(12.1);
		walk2.setEnergyLevel("Medium");
		walk2.setHappiness("Good");
		walk2.setBehaviour("Playful");
		
		when(walkRepository.findAllByOrderByIdDesc()).thenReturn(List.of(walk1, walk2));
		
		mockMvc.perform(get("/walks"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].id").value(1))
			.andExpect(jsonPath("$[0].distanceKm").value(3.2))
			.andExpect(jsonPath("$[0].durationFormatted").value("00:30:00"))
			.andExpect(jsonPath("$[0].averagePace").value(10.4))
			.andExpect(jsonPath("$[0].energyLevel").value("High"))
			.andExpect(jsonPath("$[0].happiness").value("Happy"))
			.andExpect(jsonPath("$[0].behaviour").value("Calm"))
			.andExpect(jsonPath("$[1].id").value(2));
	}
	
	@Test
	void createWalk_savesAndReturnsWalk() throws Exception {
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
		
		Walk savedWalk = new Walk();
		savedWalk.setId(10L);
		savedWalk.setDate(newWalk.getDate());
		savedWalk.setStartTime(newWalk.getStartTime());
		savedWalk.setEndTime(newWalk.getEndTime());
		savedWalk.setDistanceKm(3.5);
		savedWalk.setDurationFormatted("00:45:00");
		savedWalk.setAveragePace(9.8);
		savedWalk.setEnergyLevel("Medium");
		savedWalk.setHappiness("Very Happy");
		savedWalk.setBehaviour("Good");
		
		when(walkRepository.save(any(Walk.class))).thenReturn(savedWalk);

		
		mockMvc.perform(post("/walks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newWalk)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value("10"))
			.andExpect(jsonPath("$.date").value(savedWalk.getDate().toString()))
			.andExpect(jsonPath("$.distanceKm").value(3.5))
			.andExpect(jsonPath("$.durationFormatted").value("00:45:00"))
			.andExpect(jsonPath("$.averagePace").value(9.8))
			.andExpect(jsonPath("$.energyLevel").value("Medium"))
			.andExpect(jsonPath("$.happiness").value("Very Happy"))
			.andExpect(jsonPath("$.behaviour").value("Good"));
		
	}
}

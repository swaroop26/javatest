package com.altx.javatest;

import com.altx.javatest.data.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JavatestApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void emptyDatabaseShouldReturnEmpty() throws Exception {

		this.mockMvc.perform(get("/movies")).andDo(print()).andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());
	}

	Long movieId = null;

	@Test
	public void addMovieIsSuccessful() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		Movie mov = new Movie().setTitle("Top Gun").setRunningTimeMins(110).setStars("Tom Cruise, Kelly McGillis, Val Kilmer");
		String jsonString = mapper.writeValueAsString(mov);

		ResultActions postAction = this.mockMvc.perform(post("/movie")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.content(jsonString));


		postAction.andDo(print()).andExpect(status().isOk());
		postAction.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.title").value(mov.getTitle()))
				.andExpect(jsonPath("$.runningTimeMins").value(mov.getRunningTimeMins()));

		postAction.andDo(res -> {
			Movie m = mapper.readValue(res.getResponse().getContentAsString(), Movie.class);
			movieId = m.getId();
		});

		this.mockMvc.perform(get("/movies")).andDo(print()).andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isNotEmpty());

		this.mockMvc.perform(get("/movie/" + movieId)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value(mov.getTitle()))
				.andExpect(jsonPath("$.runningTimeMins").value(mov.getRunningTimeMins()));
	}

}

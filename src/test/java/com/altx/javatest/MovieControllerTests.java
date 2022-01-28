package com.altx.javatest;

import com.altx.javatest.data.Actor;
import com.altx.javatest.data.Movie;
import com.altx.javatest.model.MovieModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class MovieControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(1)
	public void emptyDatabaseShouldReturnEmpty() throws Exception {

		this.mockMvc.perform(get("/movies")).andDo(print()).andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());
	}

	Long movieId = null;

	@Test
	@Order(2)
	public void addMovieReturnNotFoundIfActorIsMissing() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		MovieModel mov = new MovieModel().setTitle("Top Gun").setRunningTimeMins(110)
				.setStars(Arrays.asList(1L));
		String jsonString = mapper.writeValueAsString(mov);

		ResultActions postAction = this.mockMvc.perform(post("/movie")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.content(jsonString));

		postAction.andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	@Order(3)
	public void addMovieIsSuccessful() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		Actor actor = new Actor().setId(1L).setFirstName("Tom").setLastName("Cruise");
		ResultActions actorAction = this.mockMvc.perform(post("/actor")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(actor)));

		actorAction.andDo(print()).andExpect(status().isOk());

		MovieModel mov = new MovieModel().setTitle("Top Gun").setRunningTimeMins(110)
				.setStars(Arrays.asList(1L));
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

package com.altx.javatest;

import com.altx.javatest.data.Actor;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class ActorControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(1)
	public void emptyDatabaseShouldReturnEmpty() throws Exception {

		this.mockMvc.perform(get("/actors")).andDo(print()).andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());
	}

	@Test
	@Order(2)
	public void addActorIsSuccessful() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		Actor actor = new Actor().setId(1L).setFirstName("Tom").setLastName("Cruise");
		ResultActions postAction = this.mockMvc.perform(post("/actor")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(actor)));

		postAction.andDo(print()).andExpect(status().isOk());
		postAction.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.firstName").value(actor.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(actor.getLastName()));


		this.mockMvc.perform(get("/actors")).andDo(print()).andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isNotEmpty());

		this.mockMvc.perform(get("/actor/" + actor.getId())).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(actor.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(actor.getLastName()));
	}

}

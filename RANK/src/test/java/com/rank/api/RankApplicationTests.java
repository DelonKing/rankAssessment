package com.rank.api;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;
import com.rank.api.entity.BetDTO;

@SpringBootTest
@AutoConfigureMockMvc
class RankApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	Gson gson;

	@Test
	void missingPlayer() throws Exception {

		mockMvc.perform(get("/api/getBalance/100")).andDo(print()).andExpect(status().is(400));
	}

	@Test
	void getBalanceTest() throws Exception {

		mockMvc.perform(get("/api/getBalance/1")).andDo(print()).andExpect(status().is(200))
				.andExpect(content().string(equalTo("100.00")));
	}

	@Test
	void emptyBalanceTest() throws Exception {

		mockMvc.perform(get("/api/getBalance/6")).andDo(print()).andExpect(status().is(418));
	}

	@Test
	void makeBetTest() throws Exception {

		mockMvc.perform(get("/api/getBalance/4")).andDo(print()).andExpect(status().is(200))
				.andExpect(content().string(equalTo("100.00")));

		BetDTO betDTO = new BetDTO();
		betDTO.setPlayerId(4);
		betDTO.setTransactionId(40);
		betDTO.setWagerAmount(new BigDecimal(50));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/placewager").contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(betDTO, BetDTO.class)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mockMvc.perform(get("/api/getBalance/4")).andDo(print()).andExpect(status().is(200))
				.andExpect(content().string(equalTo("50.00")));
	}

	@Test
	void logWinTest() throws Exception {

		// start with 100
		mockMvc.perform(get("/api/getBalance/2")).andDo(print()).andExpect(status().is(200))
				.andExpect(content().string(equalTo("100.00")));

		BetDTO betDTO = new BetDTO();
		betDTO.setPlayerId(2);
		betDTO.setTransactionId(20);
		betDTO.setWagerAmount(new BigDecimal(50));

		// bet 50
		mockMvc.perform(MockMvcRequestBuilders.post("/api/placewager").contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(betDTO, BetDTO.class)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		betDTO.setWinAmount(new BigDecimal(200));

		// win 200
		mockMvc.perform(MockMvcRequestBuilders.post("/api/allocatewin").contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(betDTO, BetDTO.class)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		// verify 250
		mockMvc.perform(get("/api/getBalance/2")).andDo(print()).andExpect(status().is(200))
				.andExpect(content().string(equalTo("250.00")));

	}

	@Test
	void makeBetPromo() throws Exception {

		mockMvc.perform(get("/api/getBalance/3")).andDo(print()).andExpect(status().is(200))
				.andExpect(content().string(equalTo("100.00")));

		BetDTO betDTO = new BetDTO();
		betDTO.setPlayerId(3);

		betDTO.setWagerAmount(new BigDecimal(50));
		betDTO.setPromotionCode("paper");

		for (int i = 1; i <= 5; i++) {

			betDTO.setTransactionId(i);
			mockMvc.perform(MockMvcRequestBuilders.post("/api/placewager").contentType(MediaType.APPLICATION_JSON)
					.content(gson.toJson(betDTO, BetDTO.class)).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
			betDTO.setPromotionCode(null);
		}

		mockMvc.perform(get("/api/getBalance/3")).andDo(print()).andExpect(status().is(200))
				.andExpect(content().string(equalTo("100.00")));
		
		
		betDTO.setTransactionId(0);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/placewager").contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(betDTO, BetDTO.class)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mockMvc.perform(get("/api/getBalance/3")).andDo(print()).andExpect(status().is(200))
				.andExpect(content().string(equalTo("50.00")));
	}

}

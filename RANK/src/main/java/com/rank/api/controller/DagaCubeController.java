package com.rank.api.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rank.api.entity.BetDTO;
import com.rank.api.entity.BetResponseDTO;
import com.rank.api.entity.PlayerBetsDTO;
import com.rank.api.entity.PlayerDTO;
import com.rank.api.service.DagaCubeService;

@RestController
@RequestMapping("api")
public class DagaCubeController {

	Logger logger = LoggerFactory.getLogger(DagaCubeController.class);

	@Autowired
	DagaCubeService dagaCubeService;
	
	

	/* START OF - UNSECURED ENDPOINTS */

	@GetMapping(path = "/getBalance/{playerId}")
	public ResponseEntity<BigDecimal> getbalance(@PathVariable Long playerId) {

		logger.info("get player balance called for " + playerId);

		return dagaCubeService.getPlayerBalance(playerId);
	}

	@PostMapping("/placewager")
	public ResponseEntity<BetResponseDTO> placeWager(@RequestBody BetDTO betDTO) {

		logger.info("placeWager called for "+ betDTO.getPlayerId());

		return dagaCubeService.placeWager(betDTO);
	}

	@PostMapping("/allocatewin")
	public ResponseEntity<BetResponseDTO> allocateWin(@RequestBody BetDTO betDTO) {

		logger.info("allocateWin called for " + betDTO.getPlayerId());

		return dagaCubeService.allocateWin(betDTO);
	}

	/* END OF - UNSECURED ENDPOINTS */

	/* START OF - "SECURED" ENDPOINTS */

	@PostMapping("/playerbets")
	public ResponseEntity<PlayerBetsDTO> reviewPlayer(@RequestBody PlayerDTO playerDTO) {

		logger.info("reviewPlayer  called for " + playerDTO.getPlayerUsername());

		// MOCK SECURITY
		if (playerDTO.getPassword().equals("swordfish")) {
			return dagaCubeService.getPlayerBets(playerDTO);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	/* END OF - "SECURED" ENDPOINTS */

}
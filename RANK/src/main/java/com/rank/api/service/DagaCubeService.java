package com.rank.api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rank.api.db.Bet;
import com.rank.api.db.BetRepository;
import com.rank.api.db.Player;
import com.rank.api.db.PlayerRepository;
import com.rank.api.entity.BetDTO;
import com.rank.api.entity.BetResponseDTO;
import com.rank.api.entity.PlayerBetsDTO;
import com.rank.api.entity.PlayerDTO;

@Service
public class DagaCubeService {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private BetRepository betRepository;

	public ResponseEntity<BigDecimal> getPlayerBalance(Long playerId) {

		// find player
		Optional<Player> player = playerRepository.findById(playerId);

		if (player.isEmpty()) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

		/*
		 * No money means TEAPOT
		 * 
		 * Potential fringe case of getting a negative amount, unlikely to occure but
		 * best to cater for
		 */
		if (player.get().getBalance().compareTo(BigDecimal.ZERO) <= 0) {
			return new ResponseEntity<>(player.get().getBalance(), HttpStatus.I_AM_A_TEAPOT);
		} else {

			// We are all good and have a balance
			return new ResponseEntity<>(player.get().getBalance(), HttpStatus.OK);
		}

	}

	public ResponseEntity<BetResponseDTO> placeWager(BetDTO betDTO) {
		// Should the player run out of funds, they expect the casino system to state it
		// is a teapot (418).

		/*
		 * vagueness on this requirement, since a player could bet their exact balance
		 * amount, the transaction would technically be successful and therefore
		 * shouldn't merit an HTTP code other than 200
		 * 
		 * alternatively, we are running out of funds on this transaction, making the
		 * above extract true
		 * 
		 * this would largely depend on how the upstream system handles the the response
		 * 
		 * ASSUMPTION
		 * 
		 * TEAPOT response should not be used, since upstream system has a balance and
		 * should not send a bet that is higher than the balance of the player
		 * 
		 */

		// check if player exists
		Optional<Player> player = playerRepository.findById(betDTO.getPlayerId());

		// player doesn't exists so BAD!
		if (player.isEmpty()) {

			return new ResponseEntity<BetResponseDTO>(HttpStatus.BAD_REQUEST);

		}

		// confirm idempotence of bet
		Optional<Bet> existingBet = betRepository.findByTransactionId(betDTO.getTransactionId());

		if (existingBet.isEmpty()) {
			boolean isBonusActive = false;
			// ideally this is a lookup again promotion codes
			// check promotion code
			if (betDTO.getPromotionCode() != null && betDTO.getPromotionCode().equals("paper")) {
				// verify promotion
				isBonusActive = true;
				player.get().setPromotionalBetsRemaining(4);

			} else if (player.get().getPromotionalBetsRemaining() > 0) {
				isBonusActive = true;

				player.get().setPromotionalBetsRemaining(player.get().getPromotionalBetsRemaining() - 1);
			}

			Bet bet = new Bet(betDTO, player.get(), (isBonusActive == true ? betDTO.getPromotionCode() : null));

			betRepository.save(bet);

			if (isBonusActive != true) {
				player.get().setBalance(player.get().getBalance().subtract(bet.getWagerAmount()));
				playerRepository.save(player.get());
			}

			BetResponseDTO betResponseDTO = new BetResponseDTO();
			return null;
		} else {
			// build response around
			return null;
		}

	}

	public ResponseEntity<BetResponseDTO> allocateWin(BetDTO betDTO) {

		// check if player exists
		Optional<Player> player = playerRepository.findById(betDTO.getPlayerId());

		// player doesn't exists so BAD!
		if (player.isEmpty()) {

			return new ResponseEntity<BetResponseDTO>(HttpStatus.BAD_REQUEST);

		}

		// confirm idempotencey of bet
		Optional<Bet> existingBet = betRepository.findByTransactionId(betDTO.getTransactionId());

		if (existingBet.isEmpty()) {

			// NO bet found
			// code should now die
			return null;
		} else {
			// build response around
			existingBet.get().setWin(true);
			existingBet.get().setWinAmount(betDTO.getWinAmount());

			betRepository.save(existingBet.get());

			player.get().setBalance(player.get().getBalance().add(betDTO.getWinAmount()));
			playerRepository.save(player.get());
			return null;
		}

	}

	public ResponseEntity<PlayerBetsDTO> getPlayerBets(PlayerDTO playerDTO) {

		Optional<Player> player = playerRepository.findByUserName(playerDTO.getPlayerUsername());
		
		// player doesn't exists so BAD!
				if (player.isEmpty()) {

					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

				}
				
		List<Bet> bets = betRepository.findTop10ByPlayerOrderByIdDesc(player.get());

		return new ResponseEntity<>(new PlayerBetsDTO(bets), HttpStatus.OK);
	}

};
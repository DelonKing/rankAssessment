package com.rank.api.entity;

import java.util.ArrayList;
import java.util.List;

import com.rank.api.db.Bet;

public class PlayerBetsDTO {

	List<BetDTO> bets = new ArrayList<BetDTO>();
	
	public PlayerBetsDTO() {
		
	}

	public PlayerBetsDTO(List<Bet> bets2) {
		
		for(Bet bet : bets2) {
			
			bets.add(new BetDTO(bet));
		}
	}

	public List<BetDTO> getBets() {
		return bets;
	}

	public void setBets(List<BetDTO> bets) {
		this.bets = bets;
	}
	
	
}

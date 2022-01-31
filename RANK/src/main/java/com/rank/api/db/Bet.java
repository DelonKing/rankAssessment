package com.rank.api.db;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.rank.api.entity.BetDTO;

@Entity
public class Bet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private long transactionId;
	private BigDecimal wagerAmount;
	private BigDecimal winAmount;
	private Boolean    win;
	private String     bonusCode;
	private Boolean    bonusBet;
	
	@ManyToOne(targetEntity = Player.class)
	private Player player;
	
	public Bet() {
		
	}

	public Bet(BetDTO betDTO, Player player,String bonusCode) {
		 this.transactionId = betDTO.getTransactionId();
		 this.wagerAmount = betDTO.getWagerAmount();
		 this.player = player;
		 this.bonusCode = bonusCode;
		 
		 //Bonus code is validated before being passed on to object
		 this.bonusBet = (bonusCode == null) ? false : true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public Player getPlayerId() {
		return player;
	}

	public void setPlayerId(Player playerId) {
		this.player = playerId;
	}

	public BigDecimal getWagerAmount() {
		return wagerAmount;
	}

	public void setWagerAmount(BigDecimal wagerAmount) {
		this.wagerAmount = wagerAmount;
	}

	public BigDecimal getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(BigDecimal winAmount) {
		this.winAmount = winAmount;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public String getBonusCode() {
		return bonusCode;
	}

	public void setBonusCode(String bonusCode) {
		this.bonusCode = bonusCode;
	}

	public boolean isBonusBet() {
		return bonusBet;
	}

	public void setBonusBet(boolean bonusBet) {
		this.bonusBet = bonusBet;
	}

}

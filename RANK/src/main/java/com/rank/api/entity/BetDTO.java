package com.rank.api.entity;

import java.math.BigDecimal;

import com.rank.api.db.Bet;

public class BetDTO {

	private long transactionId;
	private long playerId;
	private BigDecimal wagerAmount;
	private BigDecimal winAmount;
	private String promotionCode;

	public BetDTO() {

	}

	public BetDTO(Bet bet) {

		this.playerId = bet.getPlayerId().getId();
		this.promotionCode = bet.getBonusCode();
		this.transactionId = bet.getTransactionId();
		this.wagerAmount = bet.getWagerAmount();
		this.winAmount = bet.getWinAmount();
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
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

	public String getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}

}

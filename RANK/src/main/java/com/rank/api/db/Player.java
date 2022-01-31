package com.rank.api.db;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	private String userName;
	private BigDecimal balance;
	private int promotionalBetsRemaining;
	
	public Player() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public int getPromotionalBetsRemaining() {
		return promotionalBetsRemaining;
	}

	public void setPromotionalBetsRemaining(int promotionalBetsRemaining) {
		this.promotionalBetsRemaining = promotionalBetsRemaining;
	}
	
	
	
}

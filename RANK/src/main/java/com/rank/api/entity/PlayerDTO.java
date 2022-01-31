package com.rank.api.entity;

public class PlayerDTO {
	
	
	private String playerUsername;
    private String password;
	
    
    public PlayerDTO() {
		super();
	}


	public String getPlayerUsername() {
		return playerUsername;
	}


	public void setPlayerUsername(String playerUsername) {
		this.playerUsername = playerUsername;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
    
    
}

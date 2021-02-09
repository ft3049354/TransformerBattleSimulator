package com.transformer.model;

public class BattleResult {
	private String winningTeam;
	private boolean isArenaDestroied = false;
	private int totalAmount;
	private String[] winners;
	private String[] surviors;
	private String errorMsg;
	
	public BattleResult() {}
	
	public BattleResult(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public BattleResult(boolean isArenaDestroied) {
		this.isArenaDestroied = isArenaDestroied;
	}

	public BattleResult(String winningTeam, int totalAmount, String[] winners, String[] surviors) {
		this.winningTeam = winningTeam;
		this.totalAmount = totalAmount;
		this.winners = winners;
		this.surviors = surviors;
	}

	public String getWinningTeam() {
		return winningTeam;
	}

	public void setWinningTeam(String winningTeam) {
		this.winningTeam = winningTeam;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String[] getSurviors() {
		return surviors;
	}

	public void setSurviors(String[] surviors) {
		this.surviors = surviors;
	}

	public String[] getWinners() {
		return winners;
	}

	public void setWinners(String[] winners) {
		this.winners = winners;
	}

	public boolean isArenaDestroied() {
		return isArenaDestroied;
	}

	public void setArenaDestroied(boolean isArenaDestroied) {
		this.isArenaDestroied = isArenaDestroied;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}

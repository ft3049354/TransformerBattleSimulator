package com.transformer.model;

public class Transformer {

	private String name = "";
	private String team = "";
	private int strength = 0;
	private int intelligence = 0;
	private int speed = 0;
	private int endurance = 0;
	private int courage = 0;
	private int firepower = 0;
	private int rank = 0;
	private int skill = 0;
	private int overallRating = 0; // overallRating will be calculated with (strength + intelligence + speed + endurance + firepower)
	
	public Transformer() {
		//create a new transformer with all initialized properties
	}
	
	public Transformer(int strength, int intelligence, int speed, int endurance, int courage, int firepower, int rank) {
		this.strength = strength;
		this.intelligence = intelligence;
		this.speed = speed;
		this.endurance = endurance;
		this.courage = courage;
		this.firepower = firepower;
		this.rank = rank;
		this.overallRating = strength + intelligence + speed + endurance + firepower;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	
	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intellgence) {
		this.intelligence = intellgence;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getEndurance() {
		return endurance;
	}

	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}

	public int getCourage() {
		return courage;
	}

	public void setCourage(int courage) {
		this.courage = courage;
	}

	public int getFirepower() {
		return firepower;
	}

	public void setFirepower(int firepower) {
		this.firepower = firepower;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getSkill() {
		return skill;
	}

	public void setSkill(int skill) {
		this.skill = skill;
	}

	public int getOverallRating() {
		return overallRating;
	}
	
	
	public void calOverallRating() {
		this.overallRating = this.strength + this.intelligence + this.speed + this.endurance + this.firepower;;
	}
}

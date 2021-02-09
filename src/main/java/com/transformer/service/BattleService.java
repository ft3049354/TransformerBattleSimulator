package com.transformer.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;

import com.transformer.model.BattleResult;
import com.transformer.model.Transformer;

public class BattleService implements IBattleeService{
	private Stack<Transformer> autobots = new Stack<>();
	private Stack<Transformer> decepticons = new Stack<>();
	private ArrayList<Transformer> autobotWinners = new ArrayList<>();
	private ArrayList<Transformer> decepticonWinners = new ArrayList<>();
	private int battleAmout = 0;
	
	/**
	 * This method will simulate the battle and return the BattleResult object.
	 * @param competitors attributes strings array
	 * @return the BattleResult object
	 */
	public BattleResult startBattle(String[] competitors){
		if(this.competitorsEmptyCheck(competitors)) {
			return new BattleResult("There's no compeetitor");
		}
		if(competitors.length <= 1) {
			return new BattleResult("Please offer more than 1 competitor to start the battle");
		}
		
		BattleResult result = new BattleResult();
		ArrayList<Transformer> allTransformers = this.getAllTransformers(competitors);

		allTransformers.sort(new Comparator<Transformer>() {
			@Override
			public int compare(Transformer tf1, Transformer tf2) {
				return (tf1.getRank() > tf2.getRank()? 1 : -1);
			}
		});

		for(Transformer tf : allTransformers) {
			if(tf.getTeam().equals("A")) {
				autobots.push(tf);
			}else
				decepticons.push(tf);
		}
		
		// The battle will be ended if only one team has competitors.
		if(autobots.isEmpty() || decepticons.isEmpty()) {
			return new BattleResult("Please make sure each team has at least 1 transformer, then the battle can start.");
		}

		while(!areBothTeamsEmpty()) {
			battleAmout++;
			Transformer autobot = autobots.pop();
			Transformer decepticon = decepticons.pop();
			if(this.checkSpecialRule(autobot, decepticon)) {
				//end the battle 
				return new BattleResult(true);
			}
			Transformer winner = this.startOneRoundFaceoff(autobot, decepticon);
			if( winner!= null) {
				switch(winner.getTeam()) {
				case "A":
					this.autobotWinners.add(winner);
					break;
				case "D":
					this.decepticonWinners.add(winner);
					break;
				default:;
				}
			}
		}
		
		int autobotWinnings = autobotWinners.size();
		int decepticonWinnings = decepticonWinners.size();
		result.setTotalAmount(battleAmout);
		if(autobotWinnings > decepticonWinnings) {
			result.setWinningTeam("A");
			result.setWinners(this.getTransformerNames(autobotWinners));
			result.setSurviors(this.getTransformerNames(decepticons));
		} else if(autobotWinnings < decepticonWinnings) {
			result.setWinningTeam("D");
			result.setWinners(this.getTransformerNames(decepticonWinners));
			result.setSurviors(this.getTransformerNames(autobots));
		}
		
		return result;
	}
	
	/**
	 * Check if the attributes string array is empty
	 * @param competitors
	 * @return
	 */
	private boolean competitorsEmptyCheck(String[] competitors) {
		for(String competitor: competitors) {
			if(competitor == null || competitor.equals("")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Convert the attributes strings array to a list of transformer objects 
	 * @param competitors
	 * @return
	 */
	private ArrayList<Transformer> getAllTransformers(String[] competitors){
		ArrayList<Transformer> allTransformers = new ArrayList<>();
		for(String competitor : competitors) {
			allTransformers.add(this.createTransformer(competitor));
		}
		return allTransformers;
	}

	/**
	 * Create a transformer from attribute strings.
	 * @param competiror
	 * @return
	 */
	private Transformer createTransformer(String competiror){
		String[] attributes = competiror.split(",");
		Transformer transformer = new Transformer();
		transformer.setName(attributes[0]);
		// Capitalize the team letter 
		transformer.setTeam(attributes[1].toUpperCase());
		transformer.setStrength(Integer.valueOf(attributes[2]));
		transformer.setIntelligence(Integer.valueOf(attributes[3]));
		transformer.setSpeed(Integer.valueOf(attributes[4]));
		transformer.setEndurance(Integer.valueOf(attributes[5]));
		transformer.setRank(Integer.valueOf(attributes[6]));
		transformer.setCourage(Integer.valueOf(attributes[7]));
		transformer.setFirepower(Integer.valueOf(attributes[8]));
		transformer.setSkill(Integer.valueOf(attributes[9]));
		transformer.calOverallRating();
		return transformer;
	}

	/**
	 * Start the face off between one autobot and one decepticon
	 * @param autobot
	 * @param decepticon
	 * @return the winner. If the result is null, that means it's a tied game
	 */
	private Transformer startOneRoundFaceoff(Transformer autobot, Transformer decepticon) {
		// Check with the special rule
		if(autobot.getName().equals("Optimus Prime") || autobot.getName().equals("Predaking")) {
			return autobot;
		}else if(decepticon.getName().equals("Predaking") || decepticon.getName().equals("Optimus Prime")) {
			return decepticon;
		}
		// Check with the runaway rule
		if(checkRunAwayRule(autobot, decepticon)) {
			return autobot;
		}else if(checkRunAwayRule(decepticon, autobot)) {
			return decepticon;
		}
		// Check with the skill rule
		if(checkSkillRule(autobot, decepticon)) {
			return autobot;
		}else if(checkSkillRule(decepticon, autobot)) {
			return decepticon;
		}
		// Check with the normal rule
		if(checkNormalRule(autobot, decepticon)) {
			return autobot;
		}else if (checkNormalRule(decepticon, autobot)) {
			return decepticon;
		}
		// If none of above situations happens, then it's a tied battle
		return null;
	}

	/**
	 * If the competitors are Predaking or Optimus Prime (or duplicate), the battle will be end immediately with the arena is destroyed
	 * @param tf1
	 * @param tf2
	 * @return Compliance with the rule or not 
	 */
	private boolean checkSpecialRule(Transformer tf1, Transformer tf2) {
		//special rule
		if((tf1.getName().equals("Optimus Prime") || (tf1.getName().equals("Predaking")))
		&& ((tf2.getName().equals("Predaking") || tf2.getName().equals("Optimus Prime")))){
			return true;
		}
		return false;
	}

	/**
	 * If any fighter is down 4 or more points of courage and 3 or more points of strength compared to their opponent, the opponent automatically wins 
	 * because the opponent has ran away
	 * @param tf1
	 * @param tf2
	 * @return Compliance with the rule or not 
	 */
	private boolean checkRunAwayRule(Transformer tf1, Transformer tf2) {
		if((tf1.getCourage() - tf2.getCourage()) >=4 && (tf1.getStrength() - tf2.getStrength()) >=3) {
			return true;
		}
		return false;
	}

	/**
	 * If one of the fighters is 3 or more points of skill above their opponent, they win the fight
	 * @param tf1
	 * @param tf2
	 * @return Compliance with the rule or not 
	 */
	private boolean checkSkillRule(Transformer tf1, Transformer tf2) {
		if(tf1.getSkill() - tf2.getSkill() >= 3) {
			return true;
		}
		return false;
	}

	/**
	 * If a fighter's overall rating is higher than the opponent, he will win.
	 * overall rating formula:  (Strength + Intelligence + Speed + Endurance + Firepower)
	 * @param tf1
	 * @param tf2
	 * @return Compliance with the rule or not
	 */
	private boolean checkNormalRule(Transformer tf1, Transformer tf2) {
		if(tf1.getOverallRating() > tf2.getOverallRating()) {
			return true;
		}
		return false;
	}
	private boolean areBothTeamsEmpty() {
		if(this.autobots.size() == 0 || this.decepticons.size() == 0) {
			return true;
		}
		return false;
	}
	
	private String[] getTransformerNames(Stack<Transformer> transformers) {
		String[] names = new String[transformers.size()];
		for(int i = 0 ; i < transformers.size(); i++) {
			names[i] = transformers.get(i).getName();
		}
		return names;
	}
	
	private String[] getTransformerNames(ArrayList<Transformer> transformers) {
		String[] names = new String[transformers.size()];
		for(int i = 0 ; i < transformers.size(); i++) {
			names[i] = transformers.get(i).getName();
		}
		return names;
	}
	
	/**
	 * Clean the arena for next battle
	 */
	public void clean() {
		this.autobots = new Stack<>();
		this.decepticons = new Stack<>();
		this.autobotWinners = new ArrayList<>();
		this.decepticonWinners = new ArrayList<>();
		this.battleAmout = 0;
	}
}

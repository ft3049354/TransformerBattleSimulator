package com.transformer.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.util.Assert;

import com.transformer.model.BattleResult;

@WebMvcTest(BattleService.class)
public class BattleServiceTest {
	public static String [] competitorsInputs;
	@Autowired
	public static BattleService service;
	
	@BeforeAll
	public static void initCompetitors() {
		service = new BattleService();
	}
	
	@AfterEach
	public void clean() {
		service.clean();
	}
	
	@Test
	void special_rule_check() {
		competitorsInputs = new String[2];
		competitorsInputs[0] = "Optimus Prime,A,6,6,7,9,5,2,9,7";
		competitorsInputs[1] = "Predaking,D,6,6,7,9,5,2,9,7";
		BattleResult result = service.startBattle(competitorsInputs);
		boolean isDestroied = result.isArenaDestroied();
		Assert.isTrue(isDestroied, "All competitors are destroied, so the isDestroied should be true");
	}
	
	@Test
	void dupicate_bot_special_rule_check() {
		competitorsInputs = new String[2];
		competitorsInputs[0] = "Optimus Prime,A,6,6,7,9,5,2,9,7";
		competitorsInputs[1] = "Optimus Prime,D,6,6,7,9,5,2,9,7";
		BattleResult result = service.startBattle(competitorsInputs);
		boolean isDestroied = result.isArenaDestroied();
		Assert.isTrue(isDestroied, "All competitors are destroied, so the isDestroied should be true");
	}
	
	@Test
	void runaway_rule_check() {
		competitorsInputs = new String[2];
		competitorsInputs[0] = "TestBot1,A,7,1,1,9,1,9,1,1";
		competitorsInputs[1] = "TestBot2,D,2,9,9,2,9,1,9,9";
		BattleResult result = service.startBattle(competitorsInputs);
		Assert.isTrue(result.getWinningTeam().equals("A"), "Autobot should win by opponents running away");
	}
	
	@Test
	void skill_rule_check() {
		competitorsInputs = new String[2];
		competitorsInputs[0] = "TestBot1,A,9,1,1,1,1,9,1,9";
		competitorsInputs[1] = "TestBot2,D,9,9,9,9,9,9,9,1";
		BattleResult result = service.startBattle(competitorsInputs);
		Assert.isTrue(result.getWinningTeam().equals("A"), "Autobot should win by having higher skill points");
	}
	
	@Test
	void normal_overall_rating_rule_check() {
		competitorsInputs = new String[2];
		competitorsInputs[0] = "TestBot1,A,2,2,2,2,5,5,2,5";
		competitorsInputs[1] = "TestBot2,D,1,1,1,1,5,5,1,5";
		BattleResult result = service.startBattle(competitorsInputs);
		Assert.isTrue(result.getWinningTeam().equals("A"), "Autobot should win by having higher overall rating points");
	}
	
	@Test
	void tied_rule_check() {
		competitorsInputs = new String[2];
		competitorsInputs[0] = "TestBot1,A,1,1,1,1,5,5,1,5";
		competitorsInputs[1] = "TestBot2,D,1,1,1,1,5,5,1,5";
		BattleResult result = service.startBattle(competitorsInputs);
		Assert.isNull(result.getWinningTeam(), "no winning team, so it should be a tied game");
	}
	
	@Test
	void one_empty_teams_check() {
		competitorsInputs = new String[2];
		competitorsInputs[0] = "TestBot1,A,1,1,1,1,5,5,1,5";
		competitorsInputs[1] = "TestBot2,A,1,1,1,1,5,5,1,5";
		BattleResult result = service.startBattle(competitorsInputs);
		Assert.isTrue(result.getErrorMsg().equals("Please make sure each team has at least 1 transformer, then the battle can start."), "Each team should have at least 1 transformer");
	}
	
	@Test
	void one_competitor_check() {
		competitorsInputs = new String[1];
		competitorsInputs[0] = "TestBot1,A,1,1,1,1,5,5,1,5";
		BattleResult result = service.startBattle(competitorsInputs);
		Assert.isTrue(result.getErrorMsg().equals("Please offer more than 1 competitor to start the battle"), "There should be at least 2 competitors");
	}
	
	@Test
	void both_empty_teams_check() {
		competitorsInputs = new String[2];
		BattleResult result = service.startBattle(competitorsInputs);
		Assert.isTrue(result.getErrorMsg().equals("There's no compeetitor"), "Both teams are empty, then the error msg should be 'There's no compeetitor'");
	}
	

	
	@Test
	void multi_winners_in_one_team_check() {
		competitorsInputs = new String[5];
		competitorsInputs[0] = "TestBot1,A,1,1,1,1,5,5,1,1";
		competitorsInputs[1] = "TestBot2,D,2,1,1,1,6,5,1,1";
		competitorsInputs[2] = "TestBot3,A,3,1,1,1,7,5,1,1";
		competitorsInputs[3] = "TestBot4,D,4,1,1,1,8,5,1,1";
		competitorsInputs[4] = "TestBot5,A,5,1,1,1,9,5,1,1";
		BattleResult result = service.startBattle(competitorsInputs);
		Assert.isTrue(result.getWinners().length >= 2, "The winners amount in winning team should have more than 1");
	}
	
	@Test
	void multi_surviors_in_one_team_check() {
		competitorsInputs = new String[5];
		competitorsInputs[0] = "TestBot1,A,1,1,1,1,5,5,1,1";
		competitorsInputs[1] = "TestBot2,D,9,9,9,9,6,5,9,9";
		competitorsInputs[2] = "TestBot3,A,3,1,1,1,7,5,1,1";
		competitorsInputs[3] = "TestBot4,A,4,1,1,1,8,5,1,1";
		competitorsInputs[4] = "TestBot5,A,7,7,7,7,9,5,7,7";
		BattleResult result = service.startBattle(competitorsInputs);
		Assert.isTrue(result.getSurviors().length >= 2, "The surviors amount in loosing team is more than 1");
	}
}

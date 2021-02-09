package com.transformer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.transformer.model.BattleResult;
import com.transformer.service.BattleService;


@RestController
public class BattleController {

	@RequestMapping("/battle")
	public BattleResult simulateBattle(@RequestParam(value = "competitors[]") String[] competitors) {
		BattleResult result = null;
		try {
		BattleService service = new BattleService();
		result = service.startBattle(competitors);
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			return result;
		}
	}
}

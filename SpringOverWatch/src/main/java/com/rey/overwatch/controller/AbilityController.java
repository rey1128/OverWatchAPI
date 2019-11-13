package com.rey.overwatch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rey.overwatch.dao.Ability;
import com.rey.overwatch.dao.Hero;
import com.rey.overwatch.model.AbilityModel;
import com.rey.overwatch.service.AbilityService;
import com.rey.overwatch.service.HeroService;

@RestController
public class AbilityController {

	private final String ABILITY_ENDPOINT = "/api/ability";
	private final String ABILITY_RESOURCE_ENDPOINT = "/api/ability/{id}";

	@Autowired
	private AbilityService abilitySrv;
	@Autowired
	private HeroService heroSrv;

	@GetMapping(ABILITY_ENDPOINT)
	public List<Ability> getAbilities() {
		return abilitySrv.findAbilities();
	}

	@PostMapping(ABILITY_ENDPOINT)
	public void createAbility(@RequestBody AbilityModel model) {
		Hero hero = heroSrv.findHeroById(model.getHero_id());
		abilitySrv.createAbility(model, hero);
	}

	@GetMapping(ABILITY_RESOURCE_ENDPOINT)
	public Ability getAbilityById(@PathVariable Long id) {
		return abilitySrv.findAbilityById(id);
	}

	@PutMapping(ABILITY_RESOURCE_ENDPOINT)
	public void updateAbilityById(@PathVariable Long id, @RequestBody AbilityModel model) {
		Hero hero = null;
		if (model.getHero_id() != null)
			hero = heroSrv.findHeroById(model.getHero_id());
		abilitySrv.updateAbilityById(id, model, hero);
	}

	@DeleteMapping(ABILITY_RESOURCE_ENDPOINT)
	public void deleteAbilityById(@PathVariable Long id) {
		abilitySrv.deleteAbilityById(id);
	}

}

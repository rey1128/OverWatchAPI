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
import com.rey.overwatch.service.AbilityService;
import com.rey.overwatch.service.HeroService;

@RestController
public class HeroController {
	@Autowired
	private HeroService heroSrv;
	@Autowired
	private AbilityService abilitySrv;
	private final String HERO_ENDPOINT = "/api/hero";
	private final String HERO_RESOURCE_ENDPOINT = "/api/hero/{id}";
	private final String HERO_ABILITY_ENDPOINT = "/api/hero/{id}/abilities";

	@GetMapping("/ping")
	public String ping() {
		return "ping successfully";
	}

	@GetMapping(HERO_ENDPOINT)
	public List<Hero> getHeros() {
		return heroSrv.findHeros();
	}

	@GetMapping(HERO_RESOURCE_ENDPOINT)
	public Hero getHeroById(@PathVariable Long id) {
		return heroSrv.findHeroById(id);
	}

	@GetMapping(HERO_ABILITY_ENDPOINT)
	public List<Ability> getHeroAbilities(@PathVariable Long id) {
		Hero hero = heroSrv.findHeroById(id);
		return abilitySrv.findAbilitiesByHero(hero);
	}

	@PostMapping(HERO_ENDPOINT)
	public void createHero(@RequestBody Hero hero) {
		heroSrv.createHero(hero);
	}

	@DeleteMapping(HERO_RESOURCE_ENDPOINT)
	public void deleteHeroById(@PathVariable Long id) {
		heroSrv.deleteHeroById(id);
	}

	@PutMapping(HERO_RESOURCE_ENDPOINT)
	public void updateHeroById(@PathVariable Long id, @RequestBody Hero hero) {
		heroSrv.updateHero(id, hero);
	}
}

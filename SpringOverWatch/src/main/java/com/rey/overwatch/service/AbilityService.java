package com.rey.overwatch.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.rey.overwatch.dao.Ability;
import com.rey.overwatch.dao.Hero;
import com.rey.overwatch.model.AbilityModel;
import com.rey.overwatch.repository.AbilityRepository;

@Service
public class AbilityService {
	@Autowired
	private AbilityRepository abilityRepo;

	public List<Ability> findAbilities() {
		List<Ability> abilities = new ArrayList<>();
		abilityRepo.findAll().forEach(abilities::add);
		return abilities;
	}

	public List<Ability> findAbilitiesByHero(Hero hero) {
		return abilityRepo.findAbilityByHeroId(hero);
	}

	public Ability findAbilityById(Long id) {
		return abilityRepo.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Ability with id %s cannot be found", id)));
	}

	public void createAbility(AbilityModel model, Hero hero) {
		Ability ability = new Ability();
		ability.setDescription(model.getDescription());
		ability.setIs_ultimate(model.getIs_ultimate());
		ability.setName(model.getName());
		ability.setHero(hero);

		abilityRepo.save(ability);
	}
	
	public void createAbilities(List<Ability> abilities) {
		abilityRepo.saveAll(abilities);
	}
	

	public void updateAbilityById(Long id, AbilityModel model, Hero hero) {
		Ability ability = findAbilityById(id);

		if (model.getDescription() != null)
			ability.setDescription(model.getDescription());
		if (model.getIs_ultimate() != null)
			ability.setIs_ultimate(model.getIs_ultimate());
		if (model.getName() != null)
			ability.setName(model.getName());
		if (hero != null)
			ability.setHero(hero);

		abilityRepo.save(ability);
	}

	public void deleteAbilityById(Long id) {
		abilityRepo.deleteById(id);
	}
}

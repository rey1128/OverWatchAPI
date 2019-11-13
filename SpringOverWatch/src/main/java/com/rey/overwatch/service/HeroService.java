package com.rey.overwatch.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.rey.overwatch.dao.Hero;
import com.rey.overwatch.repository.HeroRepository;

@Service
public class HeroService {
	@Autowired
	private HeroRepository heroRepo;

	public List<Hero> findHeros() {
		List<Hero> heros = new ArrayList<>();
		heroRepo.findAll().forEach(heros::add);
		return heros;
	}

	public Hero findHeroById(long id) {
		return heroRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("hero with id %s cannot be found", id)));
	}

	public void createHero(Hero hero) {
		heroRepo.save(hero);
	}
	public void createHeros(List<Hero> heros) {
		heroRepo.saveAll(heros);
	}

	public void deleteHeroById(long id) {
		heroRepo.deleteById(id);
	}

	public void updateHero(long id, Hero newHero) {
		Hero hero = findHeroById(id);

		if (newHero.getAge() != null)
			hero.setAge(newHero.getAge());
		if (newHero.getDescription() != null)
			hero.setDescription(newHero.getDescription());
		if (newHero.getName() != null)
			hero.setName(newHero.getName());
		if (newHero.getReal_name() != null)
			hero.setReal_name(newHero.getReal_name());

		heroRepo.save(hero);

	}
}

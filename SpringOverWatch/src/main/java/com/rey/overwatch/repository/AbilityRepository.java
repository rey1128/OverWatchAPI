package com.rey.overwatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.rey.overwatch.dao.Ability;
import com.rey.overwatch.dao.Hero;

public interface AbilityRepository extends CrudRepository<Ability, Long>{
	
	@Query("SELECT a FROM Ability a WHERE a.hero=?1")
	List<Ability> findAbilityByHeroId(Hero hero);

}

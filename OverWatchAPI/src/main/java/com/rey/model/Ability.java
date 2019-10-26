package com.rey.model;

public class Ability {
	
	private int id;
	private String name;
	private String description;
	private boolean is_ultimate;
	private Hero hero;
	private int hero_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isIs_ultimate() {
		return is_ultimate;
	}
	public void setIs_ultimate(boolean is_ultimate) {
		this.is_ultimate = is_ultimate;
	}
	public Hero getHero() {
		return hero;
	}
	public void setHero(Hero hero) {
		this.hero = hero;
	}
	public int getHero_id() {
		return hero_id;
	}
	public void setHero_id(int hero_id) {
		this.hero_id = hero_id;
	}
	
	

}

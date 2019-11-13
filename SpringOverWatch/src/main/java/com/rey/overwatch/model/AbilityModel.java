package com.rey.overwatch.model;

public class AbilityModel {
	private Long id;
	private String name;
	private String description;
	private Boolean is_ultimate;
	private Long hero_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Boolean getIs_ultimate() {
		return is_ultimate;
	}

	public void setIs_ultimate(Boolean is_ultimate) {
		this.is_ultimate = is_ultimate;
	}

	public Long getHero_id() {
		return hero_id;
	}

	public void setHero_id(Long hero_id) {
		this.hero_id = hero_id;
	}

}

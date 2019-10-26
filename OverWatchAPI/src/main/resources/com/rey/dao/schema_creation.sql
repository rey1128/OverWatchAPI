create table hero(
	id int not null primary key,
	name varchar(255),
	real_name varchar(255),
	health int,
	armour int,
	shield int
);

create table ability(
	id int not null primary key,
	name varchar(255),
	description varchar(255),
	is_ultimate boolean,
	hero_id int references hero(id)
);


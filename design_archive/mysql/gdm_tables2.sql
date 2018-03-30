ALTER TABLE assert_search DROP CONSTRAINT FK_assert_search_assert;
ALTER TABLE assert_search DROP CONSTRAINT FK_assert_search_search;
DROP TABLE assert_search;
ALTER TABLE search_in_source DROP CONSTRAINT FK_search_in_source_search;
ALTER TABLE search_in_source DROP CONSTRAINT FK_search_in_source_source;
DROP TABLE search_in_source;
DROP TABLE search;

ALTER TABLE reference DROP CONSTRAINT FK_reference_assert_a;
ALTER TABLE reference DROP CONSTRAINT FK_reference_assert_b;
ALTER TABLE reference DROP CONSTRAINT FK_reference_reference_type;
DROP TABLE reference;

ALTER TABLE assert DROP CONSTRAINT FK_assert_event_rel;
ALTER TABLE assert DROP CONSTRAINT FK_assert_persona_rel;
ALTER TABLE assert DROP CONSTRAINT FK_assert_role;
ALTER TABLE assert DROP CONSTRAINT FK_assert_source;
DROP TABLE assert;

ALTER TABLE rep_source DROP CONSTRAINT FK_rep_source_source;
DROP TABLE rep_source;
DROP TABLE representation;

ALTER TABLE source_group DROP CONSTRAINT FK_source_group_source_a;
ALTER TABLE source_group DROP CONSTRAINT FK_source_group_source_b;
DROP TABLE source_group;
ALTER TABLE source DROP CONSTRAINT FK_source_place;
ALTER TABLE source DROP CONSTRAINT FK_source_written_dt;
DROP TABLE source;

ALTER TABLE event_rel DROP CONSTRAINT FK_event_event_rel_event_a;
ALTER TABLE event_rel DROP CONSTRAINT FK_event_event_rel_event_b;
ALTER TABLE event_rel DROP CONSTRAINT FK_event_rel_event_rel_type;
DROP TABLE event_rel;
ALTER TABLE role DROP CONSTRAINT FK_role_event;
ALTER TABLE role DROP CONSTRAINT FK_role_persona;
ALTER TABLE role DROP CONSTRAINT FK_role_role_type;
DROP TABLE role;
ALTER TABLE persona_rel DROP CONSTRAINT FK_persona_persona_rel_persona_a;
ALTER TABLE persona_rel DROP CONSTRAINT FK_persona_persona_rel_persona_b;
ALTER TABLE persona_rel DROP CONSTRAINT FK_persona_rel_persona_rel_type;
DROP TABLE persona_rel;

ALTER TABLE event DROP CONSTRAINT FK_event_event_type;
ALTER TABLE event DROP CONSTRAINT FK_event_place;
ALTER TABLE event DROP CONSTRAINT FK_event_start_dt;
ALTER TABLE event DROP CONSTRAINT FK_event_end_dt;
DROP TABLE event;
DROP TABLE persona;

ALTER TABLE place_group DROP CONSTRAINT FK_place_group_place_a;
ALTER TABLE place_group DROP CONSTRAINT FK_place_group_place_b;
DROP TABLE place_group;
DROP TABLE place;

DROP TABLE dt;

DROP TABLE event_rel_type;
DROP TABLE role_type;
DROP TABLE persona_rel_type;

DROP TABLE reference_type;

DROP TABLE event_type;

DROP TABLE gdm_version;
CREATE TABLE gdm_version
(
	version_number float
);
insert into gdm_version values (1.0);

CREATE TABLE event_type
(
	event_type_pk integer NOT NULL ,
	name varchar (64) NULL ,
	CONSTRAINT PK_event_type PRIMARY KEY  CLUSTERED 
	(
		event_type_pk
	)
);

CREATE TABLE reference_type
(
	reference_type_pk integer NOT NULL ,
	name varchar (64) NULL ,
	CONSTRAINT PK_reference_type PRIMARY KEY  CLUSTERED 
	(
		reference_type_pk
	)
);

CREATE TABLE persona_rel_type
(
	persona_rel_type_pk integer NOT NULL ,
	name varchar (64) NULL ,
	CONSTRAINT PK_persona_rel_type PRIMARY KEY  CLUSTERED 
	(
		persona_rel_type_pk
	)
);

CREATE TABLE role_type
(
	role_type_pk integer NOT NULL ,
	name varchar (64) NULL ,
	CONSTRAINT PK_role_type PRIMARY KEY  CLUSTERED 
	(
		role_type_pk
	)
);

CREATE TABLE event_rel_type
(
	event_rel_type_pk integer NOT NULL ,
	name varchar (64) NULL ,
	CONSTRAINT PK_event_rel_type PRIMARY KEY  CLUSTERED 
	(
		event_rel_type_pk
	)
);

CREATE TABLE dt
(
	dt_pk integer NOT NULL ,
	y integer NULL ,
	m integer NULL ,
	d integer NULL ,
	y2 integer NULL ,
	m2 integer NULL ,
	d2 integer NULL ,
	hr integer NULL ,
	mn integer NULL ,
	circa tinyint NULL ,
	julian tinyint NULL ,
	CONSTRAINT PK_dt PRIMARY KEY  CLUSTERED 
	(
		dt_pk
	)
);

CREATE TABLE place
(
	place_pk integer NOT NULL ,
	name varchar (128) NULL ,
	CONSTRAINT PK_place PRIMARY KEY  CLUSTERED 
	(
		place_pk
	)
);

CREATE TABLE place_group
(
	place_a_fk integer NOT NULL ,
	place_b_fk integer NOT NULL ,
	a_rel_a varchar (64) NULL ,
	CONSTRAINT FK_place_group_place_a FOREIGN KEY 
	(
		place_a_fk
	) REFERENCES place (
		place_pk
	),
	CONSTRAINT FK_place_group_place_b FOREIGN KEY 
	(
		place_b_fk
	) REFERENCES place (
		place_pk
	)
);

CREATE TABLE persona
(
	persona_pk integer NOT NULL ,
	name varchar (64) NULL ,
	CONSTRAINT PK_persona PRIMARY KEY  CLUSTERED 
	(
		persona_pk
	)
);

CREATE TABLE event
(
	event_pk integer NOT NULL ,
	place_fk integer NULL ,
	start_dt_fk integer NULL ,
	end_dt_fk integer NULL ,
	event_type_fk integer NULL ,
	CONSTRAINT PK_event PRIMARY KEY  CLUSTERED 
	(
		event_pk
	),
	CONSTRAINT FK_event_event_type FOREIGN KEY 
	(
		event_type_fk
	) REFERENCES event_type (
		event_type_pk
	),
	CONSTRAINT FK_event_place FOREIGN KEY 
	(
		place_fk
	) REFERENCES place (
		place_pk
	),
	CONSTRAINT FK_event_start_dt FOREIGN KEY 
	(
		start_dt_fk
	) REFERENCES dt (
		dt_pk
	),
	CONSTRAINT FK_event_end_dt FOREIGN KEY 
	(
		end_dt_fk
	) REFERENCES dt (
		dt_pk
	)
);

CREATE TABLE persona_rel
(
	persona_rel_pk integer NOT NULL ,
	persona_a_fk integer NOT NULL ,
	persona_b_fk integer NOT NULL ,
	a_rel_b integer NULL ,
	CONSTRAINT PK_persona_rel PRIMARY KEY  CLUSTERED 
	(
		persona_rel_pk
	),
	CONSTRAINT FK_persona_persona_rel_persona_a FOREIGN KEY 
	(
		persona_a_fk
	) REFERENCES persona (
		persona_pk
	),
	CONSTRAINT FK_persona_persona_rel_persona_b FOREIGN KEY 
	(
		persona_b_fk
	) REFERENCES persona (
		persona_pk
	),
	CONSTRAINT FK_persona_rel_persona_rel_type FOREIGN KEY 
	(
		a_rel_b
	) REFERENCES persona_rel_type (
		persona_rel_type_pk
	)
);

CREATE TABLE role
(
	role_pk integer NOT NULL ,
	role_type_fk integer NULL ,
	persona_fk integer NOT NULL ,
	event_fk integer NOT NULL ,
	CONSTRAINT PK_role PRIMARY KEY  CLUSTERED 
	(
		role_pk
	),
	CONSTRAINT FK_role_event FOREIGN KEY 
	(
		event_fk
	) REFERENCES event (
		event_pk
	),
	CONSTRAINT FK_role_persona FOREIGN KEY 
	(
		persona_fk
	) REFERENCES persona (
		persona_pk
	),
	CONSTRAINT FK_role_role_type FOREIGN KEY 
	(
		role_type_fk
	) REFERENCES role_type (
		role_type_pk
	)
);

CREATE TABLE event_rel
(
	event_rel_pk integer NOT NULL ,
	event_a_fk integer NOT NULL ,
	event_b_fk integer NOT NULL ,
	a_rel_b integer NULL ,
	CONSTRAINT PK_event_rel PRIMARY KEY  CLUSTERED 
	(
		event_rel_pk
	),
	CONSTRAINT FK_event_event_rel_event_a FOREIGN KEY 
	(
		event_a_fk
	) REFERENCES event (
		event_pk
	),
	CONSTRAINT FK_event_event_rel_event_b FOREIGN KEY 
	(
		event_b_fk
	) REFERENCES event (
		event_pk
	),
	CONSTRAINT FK_event_rel_event_rel_type FOREIGN KEY 
	(
		a_rel_b
	) REFERENCES event_rel_type (
		event_rel_type_pk
	)
);

CREATE TABLE source
(
	source_pk integer NOT NULL ,
	author varchar (128) NULL ,
	title varchar (128) NULL ,
	written_dt_fk integer NULL ,
	place_fk integer NULL ,
	publisher varchar (128) NULL ,
	CONSTRAINT PK_source PRIMARY KEY  CLUSTERED 
	(
		source_pk
	),
	CONSTRAINT FK_source_place FOREIGN KEY 
	(
		place_fk
	) REFERENCES place (
		place_pk
	),
	CONSTRAINT FK_source_written_dt FOREIGN KEY 
	(
		written_dt_fk
	) REFERENCES dt (
		dt_pk
	)
);

CREATE TABLE source_group
(
	source_a_fk integer NOT NULL ,
	source_b_fk integer NOT NULL ,
	a_rel_b text NULL ,
	CONSTRAINT FK_source_group_source_a FOREIGN KEY 
	(
		source_a_fk
	) REFERENCES source (
		source_pk
	),
	CONSTRAINT FK_source_group_source_b FOREIGN KEY 
	(
		source_b_fk
	) REFERENCES source (
		source_pk
	)
);

CREATE TABLE representation
(
	representation_pk integer NOT NULL ,
	transcript text NULL ,
	extern_file varchar (128) NULL ,
	CONSTRAINT PK_representation PRIMARY KEY  CLUSTERED 
	(
		representation_pk
	)
);

CREATE TABLE rep_source
(
	representation_fk integer NOT NULL ,
	source_fk integer NOT NULL ,
	CONSTRAINT FK_rep_source_representation FOREIGN KEY 
	(
		representation_fk
	) REFERENCES representation (
		representation_pk
	),
	CONSTRAINT FK_rep_source_source FOREIGN KEY 
	(
		source_fk
	) REFERENCES source (
		source_pk
	)
);

CREATE TABLE assert
(
	assert_pk integer NOT NULL ,
	event_rel_fk integer NULL ,
	role_fk integer NULL ,
	persona_rel_fk integer NULL ,
	rationale text NOT NULL ,
	source_fk integer NULL ,
	affirmed tinyint NOT NULL ,
	CONSTRAINT PK_assert PRIMARY KEY  CLUSTERED 
	(
		assert_pk
	),
	CONSTRAINT FK_assert_event_rel FOREIGN KEY 
	(
		event_rel_fk
	) REFERENCES event_rel (
		event_rel_pk
	),
	CONSTRAINT FK_assert_persona_rel FOREIGN KEY 
	(
		persona_rel_fk
	) REFERENCES persona_rel (
		persona_rel_pk
	),
	CONSTRAINT FK_assert_role FOREIGN KEY 
	(
		role_fk
	) REFERENCES role (
		role_pk
	),
	CONSTRAINT FK_assert_source FOREIGN KEY 
	(
		source_fk
	) REFERENCES source (
		source_pk
	)
);

CREATE TABLE reference
(
	reference_pk integer NOT NULL ,
	assert_a_fk integer NOT NULL ,
	assert_b_fk integer NOT NULL ,
	a_rel_b integer NOT NULL ,
	CONSTRAINT PK_reference PRIMARY KEY  CLUSTERED 
	(
		reference_pk
	),
	CONSTRAINT FK_reference_assert_a FOREIGN KEY 
	(
		assert_a_fk
	) REFERENCES assert (
		assert_pk
	),
	CONSTRAINT FK_reference_assert_b FOREIGN KEY 
	(
		assert_b_fk
	) REFERENCES assert (
		assert_pk
	),
	CONSTRAINT FK_reference_reference_type FOREIGN KEY 
	(
		a_rel_b
	) REFERENCES reference_type (
		reference_type_pk
	)
);

CREATE TABLE search
(
	search_pk integer NOT NULL ,
	description text NULL ,
	CONSTRAINT PK_search PRIMARY KEY  CLUSTERED 
	(
		search_pk
	)
);

CREATE TABLE search_in_source
(
	search_fk integer NOT NULL ,
	source_fk integer NOT NULL ,
	CONSTRAINT FK_search_in_source_search FOREIGN KEY 
	(
		search_fk
	) REFERENCES search (
		search_pk
	),
	CONSTRAINT FK_search_in_source_source FOREIGN KEY 
	(
		source_fk
	) REFERENCES source (
		source_pk
	)
);

CREATE TABLE assert_search
(
	assert_fk integer NULL ,
	search_fk integer NULL ,
	CONSTRAINT FK_assert_search_assert FOREIGN KEY 
	(
		assert_fk
	) REFERENCES assert (
		assert_pk
	),
	CONSTRAINT FK_assert_search_search FOREIGN KEY 
	(
		search_fk
	) REFERENCES search (
		search_pk
	)
);

DROP TABLE hypothesis_search;
DROP TABLE search_in_source;
DROP TABLE search;

DROP TABLE reference;

DROP TABLE hypothesis;

DROP TABLE rep_source;
DROP TABLE representation;

DROP TABLE source_group;
DROP TABLE source;

DROP TABLE event_rel;
DROP TABLE role;
DROP TABLE persona_rel;

DROP TABLE persona_attrib;
DROP TABLE event;
DROP TABLE persona;

DROP TABLE place_group;
DROP TABLE place;

DROP TABLE dt;

DROP TABLE event_rel_type;
DROP TABLE role_type;
DROP TABLE persona_rel_type;

DROP TABLE reference_type;

DROP TABLE event_type;

CREATE TABLE event_type
(
	event_type_pk integer NOT NULL,
	name varchar (64) NULL,
	CONSTRAINT PK_event_type PRIMARY KEY  CLUSTERED 
	(
		event_type_pk
	)
);

CREATE TABLE reference_type
(
	reference_type_pk integer NOT NULL,
	name varchar (64) NULL,
	CONSTRAINT PK_reference_type PRIMARY KEY  CLUSTERED 
	(
		reference_type_pk
	)
);

CREATE TABLE persona_rel_type
(
	persona_rel_type_pk integer NOT NULL,
	name varchar (64) NULL,
	CONSTRAINT PK_persona_rel_type PRIMARY KEY  CLUSTERED 
	(
		persona_rel_type_pk
	)
);

CREATE TABLE role_type
(
	role_type_pk integer NOT NULL,
	name varchar (64) NULL,
	CONSTRAINT PK_role_type PRIMARY KEY  CLUSTERED 
	(
		role_type_pk
	)
);

CREATE TABLE event_rel_type
(
	event_rel_type_pk integer NOT NULL,
	name varchar (64) NULL,
	CONSTRAINT PK_event_rel_type PRIMARY KEY  CLUSTERED 
	(
		event_rel_type_pk
	)
);

CREATE TABLE dt
(
	dt_pk integer NOT NULL,
	y integer NULL,
	m integer NULL,
	d integer NULL,
	y2 integer NULL,
	m2 integer NULL,
	d2 integer NULL,
	hr integer NULL,
	mn integer NULL,
	circa integer NULL,
	julian integer NULL,
	CONSTRAINT PK_dt PRIMARY KEY  CLUSTERED 
	(
		dt_pk
	)
);

CREATE TABLE place
(
	place_pk integer NOT NULL,
	name varchar (128) NULL,
	CONSTRAINT PK_place PRIMARY KEY  CLUSTERED 
	(
		place_pk
	)
);

CREATE TABLE place_group
(
	place_a_fk integer NOT NULL,
	place_b_fk integer NOT NULL,
	a_rel_b varchar (64) NULL,
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
	persona_pk integer NOT NULL,
	name varchar (64) NULL,
	CONSTRAINT PK_persona PRIMARY KEY  CLUSTERED 
	(
		persona_pk
	)
);

CREATE TABLE event
(
	event_pk integer NOT NULL,
	place_fk integer NULL,
	start_dt_fk integer NULL,
	end_dt_fk integer NULL,
	event_type_fk integer NULL,
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

CREATE TABLE persona_attrib
(
	persona_attrib_pk integer NOT NULL,
	persona_fk integer NOT NULL,
	event_fk integer NOT NULL,
	attrib varchar(128) NULL,
	CONSTRAINT PK_persona_attrib PRIMARY KEY  CLUSTERED 
	(
		persona_attrib_pk
	),
	CONSTRAINT FK_persona_attrib_persona FOREIGN KEY 
	(
		persona_fk
	) REFERENCES persona (
		persona_pk
	),
	CONSTRAINT FK_persona_attrib_event FOREIGN KEY 
	(
		event_fk
	) REFERENCES event (
		event_pk
	)
);

CREATE TABLE persona_rel
(
	persona_rel_pk integer NOT NULL,
	persona_a_fk integer NOT NULL,
	persona_b_fk integer NOT NULL,
	a_rel_b integer NULL,
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
	role_pk integer NOT NULL,
	role_type_fk integer NULL,
	persona_fk integer NOT NULL,
	event_fk integer NOT NULL,
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
	event_rel_pk integer NOT NULL,
	event_a_fk integer NOT NULL,
	event_b_fk integer NOT NULL,
	a_rel_b integer NULL,
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
	source_pk integer NOT NULL,
	author varchar (128) NULL,
	title varchar (128) NULL,
	full_title varchar (128) NULL,
	written_dt_fk integer NULL,
	place_fk integer NULL,
	publisher varchar (128) NULL,
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
	source_a_fk integer NOT NULL,
	source_b_fk integer NOT NULL,
	a_rel_b text NULL,
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
	representation_pk integer NOT NULL,
	transcript text NULL,
	extern_file varchar (128) NULL,
	notes text NULL,
	CONSTRAINT PK_representation PRIMARY KEY  CLUSTERED 
	(
		representation_pk
	)
);

CREATE TABLE rep_source
(
	representation_fk integer NOT NULL,
	source_fk integer NOT NULL,
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

CREATE TABLE hypothesis
(
	hypothesis_pk integer NOT NULL,
	persona_rel_fk integer NULL,
	persona_fk integer NULL,
	persona_attrib_fk integer NOT NULL,
	role_fk integer NULL,
	event_fk integer NULL,
	event_rel_fk integer NULL,
	rationale text NOT NULL,
	source_fk integer NULL,
	affirmed integer NOT NULL,
	CONSTRAINT PK_hypothesis PRIMARY KEY  CLUSTERED 
	(
		hypothesis_pk
	),
	CONSTRAINT FK_hypothesis_persona_rel FOREIGN KEY 
	(
		persona_rel_fk
	) REFERENCES persona_rel (
		persona_rel_pk
	),
	CONSTRAINT FK_hypothesis_persona FOREIGN KEY 
	(
		persona_fk
	) REFERENCES persona (
		persona_pk
	),
	CONSTRAINT FK_hypothesis_persona_attrib FOREIGN KEY 
	(
		persona_attrib_fk
	) REFERENCES persona_attrib (
		persona_attrib_pk
	),
	CONSTRAINT FK_hypothesis_role FOREIGN KEY 
	(
		role_fk
	) REFERENCES role (
		role_pk
	),
	CONSTRAINT FK_hypothesis_event FOREIGN KEY 
	(
		event_fk
	) REFERENCES event (
		event_pk
	),
	CONSTRAINT FK_hypothesis_event_rel FOREIGN KEY 
	(
		event_rel_fk
	) REFERENCES event_rel (
		event_rel_pk
	),
	CONSTRAINT FK_hypothesis_source FOREIGN KEY 
	(
		source_fk
	) REFERENCES source (
		source_pk
	)
);

CREATE TABLE reference
(
	reference_pk integer NOT NULL,
	hypothesis_a_fk integer NOT NULL,
	hypothesis_b_fk integer NOT NULL,
	a_rel_b integer NOT NULL,
	CONSTRAINT PK_reference PRIMARY KEY  CLUSTERED 
	(
		reference_pk
	),
	CONSTRAINT FK_reference_hypothesis_a FOREIGN KEY 
	(
		hypothesis_a_fk
	) REFERENCES hypothesis (
		hypothesis_pk
	),
	CONSTRAINT FK_reference_hypothesis_b FOREIGN KEY 
	(
		hypothesis_b_fk
	) REFERENCES hypothesis (
		hypothesis_pk
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
	search_pk integer NOT NULL,
	description text NULL,
	CONSTRAINT PK_search PRIMARY KEY  CLUSTERED 
	(
		search_pk
	)
);

CREATE TABLE search_in_source
(
	search_fk integer NOT NULL,
	source_fk integer NOT NULL,
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

CREATE TABLE hypothesis_search
(
	hypothesis_fk integer NULL,
	search_fk integer NULL,
	CONSTRAINT FK_hypothesis_search_hypothesis FOREIGN KEY 
	(
		hypothesis_fk
	) REFERENCES hypothesis (
		hypothesis_pk
	),
	CONSTRAINT FK_hypothesis_search_search FOREIGN KEY 
	(
		search_fk
	) REFERENCES search (
		search_pk
	)
);

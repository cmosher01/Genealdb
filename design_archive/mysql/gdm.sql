CREATE TABLE assertion (
	assertion_pk uniqueidentifier NOT NULL ,
	event_rel_fk uniqueidentifier NULL ,
	role_fk uniqueidentifier NULL ,
	persona_rel_fk uniqueidentifier NULL ,
	rationale ntext NOT NULL ,
	source_fk uniqueidentifier NULL ,
	CONSTRAINT PK_assertion PRIMARY KEY  CLUSTERED 
	(
		assertion_pk
	),
	CONSTRAINT FK_assertion_event_rel FOREIGN KEY 
	(
		event_rel_fk
	) REFERENCES event_rel (
		event_rel_pk
	),
	CONSTRAINT FK_assertion_persona_rel FOREIGN KEY 
	(
		persona_rel_fk
	) REFERENCES persona_rel (
		persona_rel_pk
	),
	CONSTRAINT FK_assertion_role FOREIGN KEY 
	(
		role_fk
	) REFERENCES role (
		role_pk
	),
	CONSTRAINT FK_assertion_source FOREIGN KEY 
	(
		source_fk
	) REFERENCES source (
		source_pk
	)
)
GO


CREATE TABLE assertion_search (
	assertion_fk uniqueidentifier NULL ,
	search_fk uniqueidentifier NULL ,
	CONSTRAINT FK_assertion_search_assertion FOREIGN KEY 
	(
		assertion_fk
	) REFERENCES assertion (
		assertion_pk
	),
	CONSTRAINT FK_assertion_search_search FOREIGN KEY 
	(
		search_fk
	) REFERENCES search (
		search_pk
	)
)
GO


CREATE TABLE event (
	event_pk uniqueidentifier NOT NULL ,
	place_fk uniqueidentifier NULL ,
	start_date_fk uniqueidentifier NULL ,
	end_date_fk uniqueidentifier NULL ,
	type_fk uniqueidentifier NULL ,
	CONSTRAINT PK_event PRIMARY KEY  CLUSTERED 
	(
		event_pk
	),
	CONSTRAINT FK_event_event_type FOREIGN KEY 
	(
		type_fk
	) REFERENCES event_type (
		event_type_pk
	),
	CONSTRAINT FK_event_place FOREIGN KEY 
	(
		place_fk
	) REFERENCES place (
		place_pk
	)
)
GO


CREATE TABLE event_rel (
	event_rel_pk uniqueidentifier NOT NULL ,
	event_a_fk uniqueidentifier NOT NULL ,
	event_b_fk uniqueidentifier NOT NULL ,
	a_rel_b uniqueidentifier NULL ,
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
)
GO

CREATE TABLE event_rel_type (
	event_rel_type_pk uniqueidentifier NOT NULL ,
	name varchar (64) NULL ,
	CONSTRAINT PK_event_rel_type PRIMARY KEY  CLUSTERED 
	(
		event_rel_type_pk
	)
)
GO


CREATE TABLE event_type (
	event_type_pk uniqueidentifier NOT NULL ,
	name varchar (64) NULL ,
	CONSTRAINT PK_event_type PRIMARY KEY  CLUSTERED 
	(
		event_type_pk
	)
)
GO


CREATE TABLE persona (
	persona_pk uniqueidentifier NOT NULL ,
	name varchar (64) NULL ,
	CONSTRAINT PK_persona PRIMARY KEY  CLUSTERED 
	(
		persona_pk
	)
)
GO


CREATE TABLE persona_rel (
	persona_rel_pk uniqueidentifier NOT NULL ,
	persona_a_fk uniqueidentifier NOT NULL ,
	persona_b_fk uniqueidentifier NOT NULL ,
	a_rel_b uniqueidentifier NULL ,
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
)
GO


CREATE TABLE persona_rel_type (
	persona_rel_type_pk uniqueidentifier NOT NULL ,
	name varchar (64) NULL ,
	CONSTRAINT PK_persona_rel_type PRIMARY KEY  CLUSTERED 
	(
		persona_rel_type_pk
	)
)
GO


CREATE TABLE place (
	place_pk uniqueidentifier NOT NULL ,
	name varchar (128) NULL ,
	CONSTRAINT PK_place PRIMARY KEY  CLUSTERED 
	(
		place_pk
	)
)
GO


CREATE TABLE place_group (
	place_a_fk uniqueidentifier NOT NULL ,
	place_b_fk uniqueidentifier NOT NULL ,
	a_rel_a varbinary (64) NULL ,
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
)
GO


CREATE TABLE reference (
	reference_pk uniqueidentifier NOT NULL ,
	assertion_a_fk uniqueidentifier NOT NULL ,
	assertion_b_fk uniqueidentifier NOT NULL ,
	a_rel_b ntext NULL ,
	CONSTRAINT PK_reference PRIMARY KEY  CLUSTERED 
	(
		reference_pk
	),
	CONSTRAINT FK_reference_assertion_a FOREIGN KEY 
	(
		assertion_a_fk
	) REFERENCES assertion (
		assertion_pk
	),
	CONSTRAINT FK_reference_assertion_b FOREIGN KEY 
	(
		assertion_b_fk
	) REFERENCES assertion (
		assertion_pk
	)
)
GO

CREATE TABLE rep_source (
	representation_fk uniqueidentifier NOT NULL ,
	source_fk uniqueidentifier NOT NULL ,
	CONSTRAINT FK_rep_source_reprentation FOREIGN KEY 
	(
		representation_fk
	) REFERENCES reprentation (
		representation_pk
	),
	CONSTRAINT FK_rep_source_source FOREIGN KEY 
	(
		source_fk
	) REFERENCES source (
		source_pk
	)
)
GO


CREATE TABLE reprentation (
	representation_pk uniqueidentifier NOT NULL ,
	transcript ntext NULL ,
	extern_file varchar (128) NULL ,
	CONSTRAINT PK_reprentation PRIMARY KEY  CLUSTERED 
	(
		representation_pk
	)
)
GO


CREATE TABLE role (
	role_pk uniqueidentifier NOT NULL ,
	type_fk uniqueidentifier NULL ,
	persona_fk uniqueidentifier NOT NULL ,
	event_fk uniqueidentifier NOT NULL ,
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
		type_fk
	) REFERENCES role_type (
		role_type_pk
	)
)
GO

CREATE TABLE role_type (
	role_type_pk uniqueidentifier NOT NULL ,
	name varchar (64) NULL ,
	CONSTRAINT PK_role_type PRIMARY KEY  CLUSTERED 
	(
		role_type_pk
	)
)
GO

CREATE TABLE search (
	search_pk uniqueidentifier NOT NULL ,
	description ntext NULL ,
	CONSTRAINT PK_search PRIMARY KEY  CLUSTERED 
	(
		search_pk
	)
)
GO


CREATE TABLE search_in_source (
	search_fk uniqueidentifier NOT NULL ,
	source_fk uniqueidentifier NOT NULL ,
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
)
GO

CREATE TABLE source (
	source_pk uniqueidentifier NOT NULL ,
	author varchar (128) NULL ,
	title varchar (128) NULL ,
	date_written datetime NULL ,
	place_fk uniqueidentifier NULL ,
	CONSTRAINT PK_source PRIMARY KEY  CLUSTERED 
	(
		source_pk
	),
	CONSTRAINT FK_source_place FOREIGN KEY 
	(
		place_fk
	) REFERENCES place (
		place_pk
	)
)
GO

CREATE TABLE source_group (
	source_a_fk uniqueidentifier NOT NULL ,
	source_b_fk uniqueidentifier NOT NULL ,
	a_rel_b ntext NULL ,
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
)
GO








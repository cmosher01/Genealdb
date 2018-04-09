select persona.name, event_type.name, evdt.d2, evdt.m2, evdt.y2, source.title
from role
inner join persona on persona_pk = persona_fk
inner join event on event_pk = event_fk
inner join event_type on event_type_fk = event_type_pk
inner join assert on role_fk = role_pk
inner join source on source_pk = source_fk
inner join dt as evdt on evdt.dt_pk = start_dt_fk
;

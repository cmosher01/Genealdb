BEGIN
CREATE (:`Persona`:`UNIQUE IMPORT LABEL` {`name`:"Christopher Alan /Mosher/", `UNIQUE IMPORT ID`:61});
CREATE (:`Persona`:`UNIQUE IMPORT LABEL` {`name`:"Linda Marilyn /Disosway/", `UNIQUE IMPORT ID`:62});
CREATE (:`Persona`:`UNIQUE IMPORT LABEL` {`name`:"Barry Rexford /Mosher/", `UNIQUE IMPORT ID`:63});
CREATE (:`Event`:`UNIQUE IMPORT LABEL` {`chronology`:"ISO", `circa`:false, `day`:3, `description`:"Barry Rexford /Mosher/", `month`:7, `place`:"Radford", `precision`:"DAYS", `type`:"name", `year`:1966, `UNIQUE IMPORT ID`:64});
CREATE (:`Event`:`UNIQUE IMPORT LABEL` {`chronology`:"ISO", `circa`:false, `day`:3, `description`:"Christopher Alan /Mosher/", `month`:7, `place`:"Radford", `precision`:"DAYS", `type`:"name", `year`:1966, `UNIQUE IMPORT ID`:65});
CREATE (:`Event`:`UNIQUE IMPORT LABEL` {`chronology`:"ISO", `circa`:true, `day`:2, `description`:"the birth of Linda Marilyn Disosway", `month`:7, `place`:"New York, USA", `precision`:"YEARS", `type`:"birth", `year`:1941, `UNIQUE IMPORT ID`:66});
CREATE (:`Event`:`UNIQUE IMPORT LABEL` {`chronology`:"ISO", `circa`:true, `day`:2, `description`:"the birth of Barry Rexford Mosher", `month`:7, `place`:"New York, USA", `precision`:"YEARS", `type`:"birth", `year`:1939, `UNIQUE IMPORT ID`:67});
CREATE (:`Event`:`UNIQUE IMPORT LABEL` {`chronology`:"ISO", `circa`:false, `day`:3, `description`:"the birth of Christopher Alan Mosher", `month`:7, `place`:"Radford", `precision`:"DAYS", `type`:"birth", `year`:1966, `UNIQUE IMPORT ID`:68});
CREATE (:`Event`:`UNIQUE IMPORT LABEL` {`chronology`:"ISO", `circa`:false, `day`:3, `description`:"Linda Marilyn /Disosway/", `month`:7, `place`:"Radford", `precision`:"DAYS", `type`:"name", `year`:1966, `UNIQUE IMPORT ID`:69});
CREATE (:`Event`:`UNIQUE IMPORT LABEL` {`chronology`:"ISO", `circa`:true, `day`:2, `description`:"", `month`:7, `place`:"New York, USA", `precision`:"YEARS", `type`:"marriage", `year`:1963, `UNIQUE IMPORT ID`:70});
CREATE (:`Citation`:`UNIQUE IMPORT LABEL` {`brief`:"birth certificate of Christopher Mosher", `UNIQUE IMPORT ID`:71});
COMMIT

BEGIN
CREATE CONSTRAINT ON (node:`UNIQUE IMPORT LABEL`) ASSERT node.`UNIQUE IMPORT ID` IS UNIQUE;
COMMIT

SCHEMA AWAIT

BEGIN
MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:63}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:71}) CREATE (n1)-[r:`CITATION`]->(n2);
MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:61}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:71}) CREATE (n1)-[r:`CITATION`]->(n2);
MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:62}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:71}) CREATE (n1)-[r:`CITATION`]->(n2);
MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:62}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:70}) CREATE (n1)-[r:`HAD_ROLE_IN` {`certainty`:6, `role`:"wife"}]->(n2);
MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:63}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:64}) CREATE (n1)-[r:`HAD_ROLE_IN` {`certainty`:10, `role`:"subject"}]->(n2);
MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:61}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:65}) CREATE (n1)-[r:`HAD_ROLE_IN` {`certainty`:10, `role`:"subject"}]->(n2);
MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:62}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:66}) CREATE (n1)-[r:`HAD_ROLE_IN` {`certainty`:10, `role`:"newborn"}]->(n2);
MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:63}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:67}) CREATE (n1)-[r:`HAD_ROLE_IN` {`certainty`:10, `role`:"newborn"}]->(n2);
MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:61}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:68}) CREATE (n1)-[r:`HAD_ROLE_IN` {`certainty`:10, `role`:"newborn"}]->(n2);
MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:62}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:68}) CREATE (n1)-[r:`HAD_ROLE_IN` {`certainty`:10, `role`:"mother"}]->(n2);
MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:63}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:68}) CREATE (n1)-[r:`HAD_ROLE_IN` {`certainty`:8, `role`:"father"}]->(n2);
MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:62}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:69}) CREATE (n1)-[r:`HAD_ROLE_IN` {`certainty`:10, `role`:"subject"}]->(n2);
MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:63}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:70}) CREATE (n1)-[r:`HAD_ROLE_IN` {`certainty`:6, `role`:"husband"}]->(n2);
MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:71}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:61}) CREATE (n1)-[r:`PERSONAE`]->(n2);
MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:71}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:63}) CREATE (n1)-[r:`PERSONAE`]->(n2);
MATCH (n1:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:71}), (n2:`UNIQUE IMPORT LABEL`{`UNIQUE IMPORT ID`:62}) CREATE (n1)-[r:`PERSONAE`]->(n2);
COMMIT

BEGIN
MATCH (n:`UNIQUE IMPORT LABEL`)  WITH n LIMIT 20000 REMOVE n:`UNIQUE IMPORT LABEL` REMOVE n.`UNIQUE IMPORT ID`;
COMMIT

BEGIN
DROP CONSTRAINT ON (node:`UNIQUE IMPORT LABEL`) ASSERT node.`UNIQUE IMPORT ID` IS UNIQUE;
COMMIT

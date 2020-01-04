FROM neo4j

ENV APOC_VERS 3.5.0.6
ENV NEO4J_AUTH none
ENV NEO4J_apoc_export_file_enabled true
ENV NEO4J_dbms_security_procedures_unrestricted=apoc.\\*

ADD --chown=neo4j:neo4j https://github.com/neo4j-contrib/neo4j-apoc-procedures/releases/download/${APOC_VERS}/apoc-${APOC_VERS}-all.jar ${NEO4J_HOME}/plugins/

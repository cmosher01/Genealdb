FROM neo4j

ENV APOC_VERS 3.5.0.8
ADD --chown=neo4j:neo4j https://github.com/neo4j-contrib/neo4j-apoc-procedures/releases/download/${APOC_VERS}/apoc-${APOC_VERS}-all.jar ${NEO4J_HOME}/plugins/

COPY neo4j.conf /var/lib/neo4j/conf/

This Dockerfile builds a neo4j image with APOC installed.

On user connect to Neo4j Browser, runs this command first:\
`:STYLE https://cmosher01.github.io/Genealdb/neo4j.grass`

Use this to export all data:\
`CALL apoc.export.cypher.all('export.plain',{format:'plain',useOptimizations:{type:'NONE'}})`

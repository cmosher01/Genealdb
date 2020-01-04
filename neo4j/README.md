This Dockerfile builds a neo4j image with APOC installed.


Use this to export all data:\
`CALL apoc.export.cypher.all('export.plain',{format:'plain',useOptimizations:{type:'NONE'}})`

#!/bin/sh

until curl -f http://neo4j:7474 ; do
  echo "Neo4j is unavailable. Waiting..." >&2
  sleep 1
done

exec genealdb neo4j

#!/bin/sh

until curl -f http://neo4j:7474 ; do
  >&2 echo "Neo4j is unavailable. Waiting..."
  sleep 1
done

genealdb $1

#!/usr/bin/env bash
echo "-- Cassandra Keyspace creation starts ---"
docker exec retail-dse-search /bin/bash cqlsh -f schema.cql
echo "-- Cassandra Keyspace creation done ---"
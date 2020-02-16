#!/bin/sh
docker-compose rm -s -f -v
docker-compose up

echo 'Waiting on consumer-dse-search until we see: "Listening for thrift clients"'
sleep 7
(docker logs -f --tail 1 retail-dse-search &) | grep -m1 'Listening for thrift clients...'
echo 'Done waiting.'

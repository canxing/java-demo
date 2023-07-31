#! /bin/bash

docker run \
  -d \
  -p 3306:3306 \
  --name mariadb \
  --env MARIADB_USER=test \
  --env MARIADB_PASSWORD=test \
  --env MARIADB_ROOT_PASSWORD=test  mariadb:11.0.2

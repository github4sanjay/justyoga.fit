#!/usr/bin/env bash

docker cp ./all_databases_insert.sql mysql:/
docker exec -it mysql bash -c "mysql -uroot -plocal < all_databases_insert.sql"
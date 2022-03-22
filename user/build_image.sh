#!/usr/bin/env bash

rm -rf target
./mvnw clean install
docker build -t justyoga-user:0.0.1-SNAPSHOT -f Dockerfile .
#!/usr/bin/env bash

rm -rf target
./mvnw clean install
docker build -t justyoga-blog:0.0.1-SNAPSHOT -f Dockerfile .
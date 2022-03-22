#!/usr/bin/env bash

echo "Build, deploy client..."
	cd ./../client/ || exit
  mvn clean install
  mvn clean deploy
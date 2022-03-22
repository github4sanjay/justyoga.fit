#!/usr/bin/env bash

echo "Build, deploy util..."
	cd ./../util/ || exit
  mvn clean install
  mvn clean deploy
#!/usr/bin/env bash

echo "Build, deploy collection..."
	cd ./../collection/ || exit
  sh ./build_image.sh
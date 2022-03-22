#!/usr/bin/env bash

echo "Build, deploy bookmark..."
	cd ./../bookmark/ || exit
  sh ./build_image.sh
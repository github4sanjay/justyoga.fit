#!/usr/bin/env bash

echo "Build, deploy auth..."
	cd ./../auth/ || exit
  sh ./build_image.sh
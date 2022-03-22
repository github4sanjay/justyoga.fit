#!/usr/bin/env bash

echo "Build, deploy library..."
	cd ./../library/ || exit
	sh ./build_image.sh
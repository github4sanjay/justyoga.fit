#!/usr/bin/env bash

echo "Build, deploy review..."
	cd ./../review/ || exit
	sh ./build_image.sh
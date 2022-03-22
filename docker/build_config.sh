#!/usr/bin/env bash

echo "Generating config-server image..."
	cd ./../config/ || exit
	sh ./build_image.sh
#!/usr/bin/env bash

echo "Generating discovery-server image..."
	cd ./../discovery/  || exit
	sh ./build_image.sh

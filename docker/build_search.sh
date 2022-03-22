#!/usr/bin/env bash

echo "Generating search service image..."
	cd ./../search/ || exit
	sh ./build_image.sh
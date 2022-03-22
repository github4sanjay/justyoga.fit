#!/usr/bin/env bash

echo "Generating location service image..."
	cd ./../location/ || exit
	sh ./build_image.sh
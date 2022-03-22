#!/usr/bin/env bash

echo "Generating gateway image..."
	cd ./../gateway/ || exit
	sh ./build_image.sh
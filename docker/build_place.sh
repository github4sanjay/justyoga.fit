#!/usr/bin/env bash

echo "Generating place service image..."
	cd ./../place/ || exit
	sh ./build_image.sh
#!/usr/bin/env bash

echo "Generating elk image..."
	cd ./../elk/  || exit
	sh ./build_image.sh
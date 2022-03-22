#!/usr/bin/env bash

echo "Build, deploy profile..."
	cd ./../profile/ || exit
	sh ./build_image.sh
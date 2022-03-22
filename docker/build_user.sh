#!/usr/bin/env bash

echo "Build, deploy user..."
	cd ./../user/ || exit
	sh ./build_image.sh
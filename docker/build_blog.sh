#!/usr/bin/env bash

echo "Build, deploy blog..."
	cd ./../blog/ || exit
	sh ./build_image.sh
#!/usr/bin/env bash

echo "Build, deploy just-yoga-ui..."
	cd ./../just-yoga-ui/ || exit
	sh ./build_image.sh
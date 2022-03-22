#!/usr/bin/env bash

# get the absolute path of the executable
SELF_PATH=$(cd -P -- "$(dirname -- "$0")" && pwd -P) && SELF_PATH="$SELF_PATH"/$(basename -- "$0")

echo "Stopping all"
docker ps | \
grep "auth\|config\|gateway\|location\|dicovery" | \
awk '{print $1}' | xargs docker stop

echo -n "Build new images? y/n"
read -r build_images
if [ "$build_images" == "y" ]; then

	echo "Generating util image..."
	sh ./build_util.sh

	echo "Generating client image..."
	sh ./build_client.sh

  echo "Generating auth image..."
	sh ./build_auth.sh

	echo "Generating blog image..."
	sh ./build_blog.sh

  echo "Generating bookmark image..."
	sh ./build_bookmark.sh

	echo "Generating collection image..."
	sh ./build_collection.sh

	echo "Generating config image..."
	sh ./build_config.sh

	echo "Generating discovery image..."
	sh ./build_discovery.sh

	echo "Generating library image..."
	sh ./build_library.sh

	echo "Generating location image..."
	sh ./build_location.sh

	echo "Generating place image..."
	sh ./build_place.sh

	echo "Generating profile image..."
	sh ./build_profile.sh

	echo "Generating review image..."
	sh ./build_review.sh

	echo "Generating search image..."
	sh ./build_search.sh

	echo "Generating user image..."
	sh ./build_user.sh

	echo "Generating gateway image..."
	sh ./build_gateway.sh

fi

echo "Starting your local dockerized full stack with mounted volumes"
cd ./../docker/ || exit
docker-compose up -d auth
docker-compose up -d user
docker-compose up -d profile
docker-compose up -d library
docker-compose up -d place
docker-compose up -d review
docker-compose up -d location
docker-compose up -d blog
docker-compose up -d collection
docker-compose up search

#!/bin/sh
set -e

TARGET_PATH="./target"

# Package and compile into jar
sbt clean assembly

for release_jar in $TARGET_PATH/*.jar
do
    jar_package=$(basename $release_jar)
    semver=$(echo $jar_package | sed -E 's/de.htwg.se.sogo-assembly-([0-9]+\.[0-9]+\.[0-9]+).jar/\1/')
    # Build the image
    docker build \
        --build-arg JAR_PACKAGE="$TARGET_PATH/$jar_package" \
        -t "bfaller/sogo:$semver" .
done

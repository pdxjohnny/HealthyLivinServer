#!/bin/sh
set -x
set -e

mkdir -pv lib

if [ ! -f lib/hamcrest-core-1.3.jar ]; then
    wget -O lib/hamcrest-core-1.3.jar \
        http://central.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
fi

if [ ! -f lib/junit-4.12.jar ]; then
    wget -O lib/junit-4.12.jar \
        http://central.maven.org/maven2/junit/junit/4.12/junit-4.12.jar
fi

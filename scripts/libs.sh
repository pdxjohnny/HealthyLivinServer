#!/bin/bash
set -x
set -e

mkdir -pv lib

wget -O lib/hamcrest-core-1.3.jar \
    http://central.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar

wget -O lib/junit-4.12.jar \
    http://central.maven.org/maven2/junit/junit/4.12/junit-4.12.jar

#!/bin/sh
set -x
set -e

NUM_JARS=1
ARTIFACT_DIR='out/artifacts/'

# Names of the jars we are building
NAME=('Web' 'Database' 'Tests')

# Download required libs (for testing)
scripts/libs.sh

# Run the build
ant -f webserver.xml

# Add the fucking manifest (thanks intellij)
until [ "$i" -ge "$NUM_JARS" ]; do
    MANIFEST="src/${NAME[i]}/META-INF/MANIFEST.MF"
    JAR="$ARTIFACT_DIR/${NAME[i]}/${NAME[i]}.jar"
    jar cfm $JAR $MANIFEST
    i=`expr $i + 1`
done

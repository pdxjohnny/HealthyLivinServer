#!/bin/sh
set -x
set -e

ARTIFACT_DIR='out/artifacts/'

# Names of the jars we are building
NAME=('Web' 'Database' 'Tests')

# Download required libs (for testing)
scripts/libs.sh

# Run the build
ant -f webserver.xml

i=0
length=${#NAME}
length=$((length + 0))
# Add the fucking manifest (thanks intellij)
until [ $((i >= $length)) ]; do
    MANIFEST="src/${NAME[i]}/META-INF/MANIFEST.MF"
    JAR="$ARTIFACT_DIR/${NAME[i]}/${NAME[i]}.jar"
    jar -umf $MANIFEST $JAR
    i=$((i + 1))
done

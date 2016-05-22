#!/bin/bash
set -x

if [ -z "$PORT" ]; then
    PORT=80
fi
if [ -z "$HOST" ]; then
    HOST="https://healthylivin.carpoolme.net"
fi
SERVER="$HOST:$PORT"

login () {
    curl --ssl --tlsv1.2 -w "\n" -X POST --data "$*" ${SERVER}/api/login/
}

$1 "${@:2}"

#!/bin/bash
set -e

if [ -z "$PORT" ]; then
    PORT=8000
fi
if [ -z "$HOST" ]; then
    HOST="http://172.17.0.2"
fi
SERVER="$HOST:$PORT"

login () {
    curl -w "\n" -X POST --data "$*" ${SERVER}/api/login/
}

$1 "${@:2}"

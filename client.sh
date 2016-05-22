#!/bin/bash

if [ -z "$PORT" ]; then
    PORT=8000
fi
if [ -z "$HOST" ]; then
    HOST="http://localhost"
fi
SERVER="$HOST:$PORT"


odd () {
    curl -w '\n' ${SERVER}/odd
}

odd_by_10() {
    curl -w '\n' ${SERVER}/odd/10
}

odd_not_3_or_5 () {
    curl -w '\n' ${SERVER}/on
}

input () {
    curl -w '\n' -X POST --data "$*" ${SERVER}/input
}

employee () {
    curl -w '\n' -X POST --data "$*" ${SERVER}/add/employee
}

login () {
    curl -w '\n' -X POST --data "$*" ${SERVER}/api/login/
}

$1 "${@:2}"

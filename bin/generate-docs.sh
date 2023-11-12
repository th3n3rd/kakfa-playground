#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"

cd "$SCRIPT_DIR/.."

./mvnw test -Dgroups=docs

rm -rf ./docs
mkdir ./docs
cp -r target/spring-modulith-docs/* docs

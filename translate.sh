#!/bin/bash

if [ -L $0 ]
then
    SOURCE="$(readlink ${BASH_SOURCE[0]})"
    DIR="$( cd "$( dirname "$SOURCE" )" && pwd )"
else
    DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
fi

java -jar $DIR/target/zx-trans-1.0.jar "$@" --config="$DIR/"
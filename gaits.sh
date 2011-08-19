#!/bin/sh
mvn exec:java -q -Dexec.mainClass="com.pomortsev.gaits.Gaits" -Dexec.args="$*"

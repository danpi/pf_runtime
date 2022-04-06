#!/bin/bash
inputTopic=${1}
outputTopic=${2}

# shellcheck disable=SC2004
echo "${inputTopic}"
echo "${outputTopic}"
java -jar ../target/pf_runtime-1.0-SNAPSHOT-jar-with-dependencies.jar "${inputTopic}" "${outputTopic}"

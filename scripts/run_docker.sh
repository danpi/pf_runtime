#!/bin/bash
inputTopic=${1}
outputTopic=${2}

# shellcheck disable=SC2004
bin/pulsar standalone &
sleep 30

echo "${inputTopic}"
echo "${outputTopic}"
java -jar /usr/local/pulsar/pf_runtime/target/pf_runtime-1.0-SNAPSHOT-jar-with-dependencies.jar "${inputTopic}" "${outputTopic}"
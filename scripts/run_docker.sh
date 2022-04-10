#!/bin/bash
inputTopic=${1}
outputTopic=${2}

# shellcheck disable=SC2004
bin/pulsar standalone -nss &
sleep 30

echo "${inputTopic}"
echo "${outputTopic}"
cd /usr/local/pulsar/pf_runtime/scripts
java -jar ../target/pf_runtime-1.0-SNAPSHOT-jar-with-dependencies.jar "${inputTopic}" "${outputTopic}"
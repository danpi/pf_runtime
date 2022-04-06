#!/bin/bash
inputStr=${1}
len=${#inputStr}
# shellcheck disable=SC2004
for ((i=$len - 1; i>=0; i--))
  do
    reverse="$reverse${inputStr:$i:1}"
    done
echo "${reverse}"
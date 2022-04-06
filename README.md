# pf_runtime
=============

pulsar-function runtime provides function based on Pulsar.
 
## Design Document
The design document is in this directory:pf_runtime/pulsar_function.pdf

## Installation
You can run in local or by Docker.
### local
#### precondition
Make sure you had run Pulsar in standalone mode on your machine.
You can install it by following the documentation below.
https://pulsar.apache.org/docs/zh-CN/next/standalone/
#### init 
1. git clone this project.
2. ./pf_runtime/script/run.sh {input_topic} {output+topic}
#### example
1. subscribe to the topic
`./pulsar-client consume my_output -s "output_topic_c"`
2. send a raw message to Pulsar
`./pulsar-client produce my_input --messages "111 222 333"`
3. receive the reverse message
` "333 222 111"`

### docker
#### pull images
docker pull danpi1hao/pulsarfunction:v1.0.0
#### run
docker run -it -p 6650:6650  -p 8080:8080 --mount source=pulsardata,target=/pulsar/data --mount source=pulsarconf,target=/pulsar/conf danpi1hao/pulsarfunction:v1.0.0 /usr/local/pulsar/pf_runtime/scripts/run_docker.sh "input_topic" "output_topic"
#### example is same as above
1. subscribe to the topic
`./pulsar-client consume my_output -s "output_topic_c"`
2. send a raw message to Pulsar
`./pulsar-client produce my_input --messages "111 222 333"`
3. receive the reverse message
` "333 222 111"`
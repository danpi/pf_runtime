# pf_runtime
=============

pulsar-function runtime provides function based on Pulsar.
 

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

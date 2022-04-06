FROM apachepulsar/pulsar:2.9.1

MAINTAINER houbonan "1522307675@qq.com"

COPY target/pf_runtime-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/local/pulsar/pf_runtime/target/
COPY target /usr/local/pulsar/pf_runtime/target/
COPY src /usr/local/pulsar/pf_runtime/src
COPY scripts /usr/local/pulsar/pf_runtime/scripts

RUN chmod 755 /usr/local/pulsar/pf_runtime/scripts/run_docker.sh
RUN chmod 755 /usr/local/pulsar/pf_runtime/scripts/reverse.sh
RUN chmod 755 /usr/local/pulsar/pf_runtime/target/pf_runtime-1.0-SNAPSHOT-jar-with-dependencies.jar
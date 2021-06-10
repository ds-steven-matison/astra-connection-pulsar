Build the project as follows:

```
git clone https://github.com/ds-steven-matison/astra-connection-pulsar.git
cd astra-connection-pulsar/java/astraconnection
mvn compile
```

Create required ENV Variables:

```
export ASTRA_SERVICE_URL=[paste Broker Service URL here]
export ASTRA_TOKEN=[paste token here]
export ASTRA_TOPIC=persistent://pulsar-poc/default/pulsar-poc
```

To execute the project run the folowing commands:

```
mvn exec:java -Dexec.mainClass="astraconnection.SimpleProducer" -Dexec.args="testing command line message"
mvn exec:java -Dexec.mainClass="astraconnection.SimpleReader"
mvn exec:java -Dexec.mainClass="astraconnection.SimpleProducer"
```
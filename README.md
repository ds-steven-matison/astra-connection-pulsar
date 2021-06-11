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

To execute the Astra Streaming Simple Producer, Reader, or Consumer run the following commands:

```
mvn exec:java -Dexec.mainClass="astraconnection.SimpleProducer" -Dexec.args="testing command line message"
mvn exec:java -Dexec.mainClass="astraconnection.SimpleReader"
mvn exec:java -Dexec.mainClass="astraconnection.SimpleConsumer"
```

To test Pulsar Kafka Adaptor run this command:

```
mvn exec:java -Dexec.mainClass="astraconnection.KafkaProducer"
```

To test the Astra Driver Connection modify DriverConnect.java with your credentials, provide path to secure bundle, and run this command:

```
mvn exec:java -Dexec.mainClass="astraconnection.DriverConnect"
```

# Driver Notes
This repo was original an example of 3.8.  Using this project one could easily test different versions of the driver.  Just be sure to stay 3.8+, and ideally 3.10.2+ for connecting to Astra.
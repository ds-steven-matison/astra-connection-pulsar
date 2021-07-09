package astraconnection;
import java.io.File;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.PulsarKafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaProducer {

private static final String SERVICE_URL = System.getenv("ASTRA_SERVICE_URL");

    public static String message;

    public static void main(String[] args) throws IOException
    {

        String message = String.join(" ", args);

        // new kafka adaptor code
        // Topic needs to be a regular Pulsar topic
        String topic = System.getenv("ASTRA_TOPIC");

        Map<String,String> map = new HashMap<String,String>();
        map.put("token", System.getenv("ASTRA_TOKEN"));

        Properties props = new Properties();
        // Point to a Pulsar service
        props.put("bootstrap.servers", SERVICE_URL);
        props.put("pulsar.authentication.class", "org.apache.pulsar.client.impl.auth.AuthenticationTls");
        props.put("pulsar.authentication.params.map", map);
        props.put("pulsar.use.tls", true);
        props.put("pulsar.tls.allow.insecure.connection", false);
        props.put("pulsar.tls.trust.certs.file.path", "/nifi/ssl/cacerts");
        props.put("key.serializer", IntegerSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        @SuppressWarnings("resource")
        Producer<Integer, String> producer = new PulsarKafkaProducer<>(props);

        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<Integer, String>(topic, i, message + " " + i));
            System.out.printf("Message [%s] sent successfully",  message + " " + i);
        }

        producer.close();
    }

     private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
}
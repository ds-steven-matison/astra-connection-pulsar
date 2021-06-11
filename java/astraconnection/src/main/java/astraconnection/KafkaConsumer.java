package astraconnection;
import java.io.File;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.PulsarKafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;


public class KafkaConsumer {

private static final String SERVICE_URL = System.getenv("ASTRA_SERVICE_URL");

    public static String message;

    public static void main(String[] args) throws IOException
    {

        String message = String.join(" ", args);

        String topic = System.getenv("ASTRA_TOPIC");

        Map<String,String> map = new HashMap<String,String>();
        map.put("token", System.getenv("ASTRA_TOKEN"));

        Properties props = new Properties();
        props.put("bootstrap.servers", SERVICE_URL);
        props.put("pulsar.authentication.class", "org.apache.pulsar.client.impl.auth.AuthenticationTls");
        props.put("pulsar.authentication.params.map", map);
        props.put("pulsar.use.tls", true);
        props.put("pulsar.tls.allow.insecure.connection", false);
        props.put("pulsar.tls.trust.certs.file.path", "");
        props.put("group.id", "my-subscription");
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", IntegerDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        @SuppressWarnings("resource")
        Consumer<Integer, String> consumer = new PulsarKafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));

        while (true) {
            ConsumerRecords<Integer, String> records = consumer.poll(100);
            records.forEach(record -> {
                System.out.printf("Received record: %s",  record);
            });

            // Commit last offset
            consumer.commitSync();
        }
    }

}
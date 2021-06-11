package astraconnection;
import java.io.File;
import org.apache.pulsar.client.api.*;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleProducer {

private static final String SERVICE_URL = System.getenv("ASTRA_SERVICE_URL");

    public static String message;

    public static void main(String[] args) throws IOException
    {

        String message = String.join(" ", args);

        // Create client object
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(SERVICE_URL)
                .authentication(
                    AuthenticationFactory.token(System.getenv("ASTRA_TOKEN"))
                )
                .build();

        // Create producer on a topic
        Producer<byte[]> producer = client.newProducer()
                .topic(System.getenv("ASTRA_TOPIC"))
                .create();

        // Send a message to the topic
        producer.send(message.getBytes());
        System.out.printf("Message sent: %s",  message);

        //Close the producer
        producer.close();

        // Close the client
        client.close();

    }

    private static final Logger log = LoggerFactory.getLogger(SimpleProducer.class);
}
package astraconnection;
import org.apache.pulsar.client.api.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SimpleConsumer {

        private static final String SERVICE_URL = System.getenv("ASTRA_SERVICE_URL");

        public static void main(String[] args) throws IOException
        {

            // Create client object
            PulsarClient client = PulsarClient.builder()
                    .serviceUrl(SERVICE_URL)
                    .authentication(
                        AuthenticationFactory.token(System.getenv("ASTRA_TOKEN"))
                    )
                    .build();

            // Create consumer on a topic with a subscription
            Consumer consumer = client.newConsumer()
                    .topic(System.getenv("ASTRA_TOPIC"))
                    .subscriptionName("my-subscription")
                    .subscribe();

            boolean receivedMsg = false;
            // Loop until a message is received
            do {
                // Block for up to 1 second for a message
                Message msg = consumer.receive(1, TimeUnit.SECONDS);

                if(msg != null){
                    System.out.printf("Message received: %s", new String(msg.getData()));

                    // Acknowledge the message to remove it from the message backlog
                    consumer.acknowledge(msg);

                    receivedMsg = true;
                }

            } while (!receivedMsg);

            //Close the consumer
            consumer.close();

            // Close the client
            client.close();

        }

}
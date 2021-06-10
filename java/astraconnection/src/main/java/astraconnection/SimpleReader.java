package astraconnection;
import org.apache.pulsar.client.api.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SimpleReader {

    private static final String SERVICE_URL = System.getenv("ASTRA_SERVICE_URL");

    public static void main(String[] args) throws IOException {

        // Create client object
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(SERVICE_URL)
                .authentication(
                    AuthenticationFactory.token(System.getenv("ASTRA_TOKEN"))
                )
                .build();

        // Create a reader on a topic starting at the earliest retained message
        // No subscription is necessary. Depending on retention policy, the
        // earliest message may be days old
        Reader<byte[]> reader = client.newReader()
                .topic(System.getenv("ASTRA_TOPIC"))
                .startMessageId(MessageId.earliest)
                .create();

        boolean receivedMsg = false;
        // Loop until a message is received
        do {
            // Block for up to 1 second for a message
            Message msg = reader.readNext(1, TimeUnit.SECONDS);

            if(msg != null){
                System.out.printf("Message received: %s%n",  new String(msg.getData()));

                receivedMsg = true;
            }

        } while (!receivedMsg);

        //Close the reader
        reader.close();

        // Close the client
        client.close();

    }
}
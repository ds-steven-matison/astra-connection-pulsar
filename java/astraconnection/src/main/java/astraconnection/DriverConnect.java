package astraconnection;
import java.io.File;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.SocketOptions;
/**
 * Hello world!
 *
 */
public class DriverConnect 
{
    public static void main( String[] args )
    {
        Cluster cluster = Cluster.builder()
            .withCloudSecureConnectBundle(new File("../../secure-connect-bundle.zip"))
            .withCredentials("demo", "demo2020")
            .withSocketOptions(
                new SocketOptions()
                        .setConnectTimeoutMillis(2000))
            .build();
        Session session = cluster.connect("demo");
        ResultSet rs = session.execute("select release_version from system.local");
        Row row = rs.one();
        if (row != null) {
            System.out.println(row.getString("release_version"));
        } else {
            System.out.println("An error occurred.");
        }
    }
}
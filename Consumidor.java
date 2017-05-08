import com.rabbitmq.client.*;
import java.io.IOException;

/**
 * Created by ivan on 26/04/17.
 */

public class Consumidor extends DefaultConsumer {

    Consumidor(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
    }
}

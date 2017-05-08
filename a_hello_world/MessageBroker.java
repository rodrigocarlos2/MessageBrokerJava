package a_hello_world;

import com.rabbitmq.client.*;
import emitter_recv.Emitter;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by aluno on 26/04/17.
 */

public class MessageBroker implements Runnable {
    private final static String QUEUE_NAME = "hello";
    private static String receivedMsg = "";

    public static void receive(String msg) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = null;
        try {
            connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    receivedMsg = message;
                    System.out.println(" [x] Received '" + message + "'");

                    Emitter.send(receivedMsg, "10.180.42.52");
                }
            };
            channel.basicConsume(QUEUE_NAME, true, consumer);
        } catch (IOException ignored) { } catch (TimeoutException ignored) { }
    }

    @Override
    public void run() {
        receive("");
    }

    public static void main(String[] args) {
        new Thread(new MessageBroker()).start();
    }

}

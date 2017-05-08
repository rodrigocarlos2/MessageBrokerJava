import com.rabbitmq.client.*;

/**
 * Created by ivan on 26/04/17.
 */

public class Receiver {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] topics) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueName = channel.queueDeclare().getQueue();

        if (topics.length < 1) {
            System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
            System.exit(1);
        }

        for (String topic : topics) channel.queueBind(queueName, EXCHANGE_NAME, topic);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        Consumer consumer1 = new Consumidor(channel);
        channel.basicConsume(queueName, true, consumer1);
    }

}

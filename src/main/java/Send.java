import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class Send {
    private static final String TASK_QUEUE_NAME = "task_queue";
    private final static String QUEUE_NAME = "UPDATED_DRUG";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("35.240.160.38");
        factory.setUsername("isofh");
        factory.setPassword("isofh123");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            Map<String, Object> args = new HashMap<>();
            args.put("x-dead-letter-exchange", QUEUE_NAME);
            channel.queueDeclare(QUEUE_NAME, true, false, false, args);
            String message = "Hello World!";
            AMQP.BasicProperties pros = new AMQP.BasicProperties.Builder()
                    .contentType("text/plain")
                    .deliveryMode(2)
                    .priority(1)
                    .build();
            channel.basicPublish("", QUEUE_NAME, pros, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}

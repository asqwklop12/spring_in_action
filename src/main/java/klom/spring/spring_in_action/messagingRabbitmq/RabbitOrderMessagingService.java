package klom.spring.spring_in_action.messagingRabbitmq;

import klom.spring.spring_in_action.Order;
import klom.spring.spring_in_action.messaging.OrderMessagingService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitOrderMessagingService implements OrderMessagingService {
private final RabbitTemplate rabbitTemplate;

  public RabbitOrderMessagingService(
      RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  public void sendOrder(Order order) {
    MessageConverter converter = rabbitTemplate.getMessageConverter();
//    MessageProperties props = new MessageProperties();
//    props.setHeader("X_ORDER_SOURCE","WEB");
//    Message message = converter.toMessage(order, props);
//    rabbitTemplate.send("tacocloud.order",message);
    rabbitTemplate.convertAndSend("tacocloud.order.queue", order,
        message -> {
          MessageProperties props = new MessageProperties();
          props.setHeader("X_ORDER_SOURCE","WEB");
          return message;
        });
  }
}

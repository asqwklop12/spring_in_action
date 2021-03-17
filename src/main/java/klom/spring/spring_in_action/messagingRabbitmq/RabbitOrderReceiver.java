package klom.spring.spring_in_action.messagingRabbitmq;

import klom.spring.spring_in_action.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Component
public class RabbitOrderReceiver {

  private final RabbitTemplate rabbit;
  private final MessageConverter converter;

  public RabbitOrderReceiver(RabbitTemplate rabbit, MessageConverter converter) {
    this.rabbit = rabbit;
    this.converter = converter;
  }

  public Order receiveOrder() {
    return rabbit.receiveAndConvert("tacocloud.orders",
        new ParameterizedTypeReference<>() {});
  }

}

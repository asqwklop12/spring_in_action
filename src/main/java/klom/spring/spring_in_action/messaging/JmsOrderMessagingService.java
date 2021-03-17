package klom.spring.spring_in_action.messaging;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import klom.spring.spring_in_action.Order;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {


  private final JmsTemplate jmsTemplate;
  private final Destination orderQueue;

  public JmsOrderMessagingService(JmsTemplate jmsTemplate, Destination orderQueue) {
    this.jmsTemplate = jmsTemplate;
    this.orderQueue = orderQueue;
  }

  @Override
  public void sendOrder(Order order) {
    jmsTemplate.send(
//        orderQueue
        "tacocloud.order.queue"
        , session -> session.createObjectMessage(order));
  }
  public void sendOrderAfter(Order order) {
    jmsTemplate.convertAndSend(
        "tacocloud.order.queue",
        order,
        message -> {
          message.setStringProperty("X_ORDER_SOURCE","WEB");
          return message;
        }
    );
  }

}

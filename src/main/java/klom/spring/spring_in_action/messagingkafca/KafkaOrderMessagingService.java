package klom.spring.spring_in_action.messagingkafca;

import klom.spring.spring_in_action.Order;
import klom.spring.spring_in_action.messaging.OrderMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaOrderMessagingService implements OrderMessagingService {

  private final KafkaTemplate<String,Order> kafkaTemplate;

  public KafkaOrderMessagingService(
      KafkaTemplate<String, Order> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void sendOrder(Order order) {
//    kafkaTemplate.send("tacocloud.orders.topic",order);
    kafkaTemplate.sendDefault(order);
  }
}

package klom.spring.spring_in_action.messagingkafca;

import klom.spring.spring_in_action.Order;
import klom.spring.spring_in_action.messaging.KitchenUi;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderListener {

  private final KitchenUi ui;

  public OrderListener(KitchenUi ui) {
    this.ui = ui;
  }

  @KafkaListener(topics = "tacocloud.orders.topic")
  public void handle(Order order) {
    ui.displayOrder(order);
  }

  @KafkaListener(topics = "tacocloud.orders.topic")
  public void handle(Order order, ConsumerRecord<Order, Void> record) {
//    ui.displayOrder(order);
    log.info("Received from partition {} with timestamp {}", record.partition(),
        record.timestamp());
    ui.displayOrder(order);
  }

  @KafkaListener(topics = "tacocloud.orders.topic")
  public void handle(Order order, Message<Order> message) {
    MessageHeaders headers = message.getHeaders();
    log.info("Received from partition {} with timestamp {}",
        headers.get(KafkaHeaders.RECEIVED_PARTITION_ID),
        headers.get(KafkaHeaders.RECEIVED_TIMESTAMP));
    ui.displayOrder(order);
  }

}

package klom.spring.spring_in_action.messagingRabbitmq;

import klom.spring.spring_in_action.Order;
import klom.spring.spring_in_action.messaging.KitchenUi;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {
  private final KitchenUi ui;

  public OrderListener(KitchenUi ui) {
    this.ui = ui;
  }

  @RabbitListener(queues = "tacocloud.order.queue")
  public void receive(Order order) {
    ui.displayOrder(order);
  }

}

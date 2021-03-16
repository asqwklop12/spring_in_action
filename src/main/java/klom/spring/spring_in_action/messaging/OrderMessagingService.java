package klom.spring.spring_in_action.messaging;

import klom.spring.spring_in_action.Order;

public interface OrderMessagingService {
  void sendOrder(Order order);
}

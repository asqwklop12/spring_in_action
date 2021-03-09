package klom.spring.spring_in_action.data;

import klom.spring.spring_in_action.Order;

public interface OrderRepository {

  Order save(Order order);
}

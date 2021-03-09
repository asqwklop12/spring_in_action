package klom.spring.spring_in_action.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import klom.spring.spring_in_action.Order;
import klom.spring.spring_in_action.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcOrderRepository implements OrderRepository {

  private final SimpleJdbcInsert orderInsert;
  private final SimpleJdbcInsert orderTacoInsert;
  private final ObjectMapper objectMapper;

  @Autowired
  public JdbcOrderRepository(JdbcTemplate jdbc) {
    this.orderInsert = new SimpleJdbcInsert(jdbc)
        .withTableName("Taco_Order")
        .usingGeneratedKeyColumns("id");
    this.orderTacoInsert = new SimpleJdbcInsert(jdbc)
        .withTableName("Taco_Order_Tacos");
    this.objectMapper = new ObjectMapper();
  }

  @Override
  public Order save(Order order) {
    order.setPlacedAt(new Date());
    long orderId = saveOrderDetails(order);
    order.setId(orderId);
    List<Taco> tacos = order.getTacos();
    for (Taco taco : tacos) {
      saveTacoToOrder(taco, orderId);
    }
    return order;
  }

  private long saveOrderDetails(Order order) {
    Map<String, Object> values = objectMapper.convertValue(order, Map.class);
    values.put("placedAt", order.getPlacedAt());
    return orderInsert.executeAndReturnKey(values)
        .longValue();
  }

  private void saveTacoToOrder(Taco taco, long orderId) {
    HashMap<String, Object> values = new HashMap<>();
    values.put("tacoOrder", orderId);
    values.put("taco", taco.getId());
    orderTacoInsert.execute(values);
  }

}

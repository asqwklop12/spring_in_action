package klom.spring.spring_in_action.data;

import java.util.List;
import klom.spring.spring_in_action.Ingredient;
import klom.spring.spring_in_action.Order;
import klom.spring.spring_in_action.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends JpaRepository<Order, String> {

  List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}

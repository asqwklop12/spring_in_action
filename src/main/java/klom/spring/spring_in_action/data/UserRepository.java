package klom.spring.spring_in_action.data;

import klom.spring.spring_in_action.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
}

package klom.spring.spring_in_action.data;

import klom.spring.spring_in_action.Taco;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends JpaRepository<Taco, Long> {

}
package klom.spring.spring_in_action.data;

import klom.spring.spring_in_action.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {

}
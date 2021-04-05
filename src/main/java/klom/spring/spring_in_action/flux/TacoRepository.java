package klom.spring.spring_in_action.flux;

import klom.spring.spring_in_action.Taco;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TacoRepository extends ReactiveCrudRepository<Taco,Long> {

}

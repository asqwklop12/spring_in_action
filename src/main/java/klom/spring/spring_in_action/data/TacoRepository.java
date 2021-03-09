package klom.spring.spring_in_action.data;

import klom.spring.spring_in_action.Taco;

public interface TacoRepository {
  Taco save(Taco taco);
}

package klom.spring.spring_in_action.email;

import java.util.ArrayList;
import java.util.List;
import klom.spring.spring_in_action.Taco;
import lombok.Data;

@Data
public class Order {
  private final String email;
  private List<Taco> tacos = new ArrayList<>();

  public void addTaco(Taco taco) {
    this.tacos.add(taco);
  }

}

package klom.spring.spring_in_action.web.api;

import klom.spring.spring_in_action.Ingredient;
import klom.spring.spring_in_action.Ingredient.Type;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;

public class IngredientModel extends EntityModel<Ingredient> {

  @Getter
  private final String name;
  @Getter
  private final Type type;

  public IngredientModel(Ingredient ingredients) {
    this.name = ingredients.getName();
    this.type = ingredients.getType();
  }
}

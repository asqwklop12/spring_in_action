package klom.spring.spring_in_action.web.api;

import java.util.Date;
import klom.spring.spring_in_action.Taco;
import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "taco", collectionRelation = "tacos")
public class TacoModel extends EntityModel<Taco> {

  private static final IngredientResourceAssembler ingredientAssembler = new IngredientResourceAssembler();
  @Getter
  private final String name;
  @Getter
  private final Date createdAt;
  @Getter
  private final CollectionModel<IngredientModel> ingredients;

  public TacoModel(Taco taco) {
    this.name = taco.getName();
    this.createdAt = taco.getCreatedAt();
//    this.ingredients = taco.getIngredients();
    this.ingredients =
        ingredientAssembler.toCollectionModel(taco.getIngredients());
  }
}

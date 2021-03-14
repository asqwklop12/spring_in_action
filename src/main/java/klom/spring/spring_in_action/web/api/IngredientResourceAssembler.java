package klom.spring.spring_in_action.web.api;

import klom.spring.spring_in_action.Ingredient;
import klom.spring.spring_in_action.Taco;
import klom.spring.spring_in_action.web.IngredientByIdConverter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class IngredientResourceAssembler extends RepresentationModelAssemblerSupport<Ingredient, IngredientModel> {


  public IngredientResourceAssembler() {
    super(IngredientByIdConverter.class, IngredientModel.class);
  }

  @Override
  protected IngredientModel instantiateModel(Ingredient ingredient) {
    return new IngredientModel(ingredient);
  }

  @Override
  public IngredientModel toModel(Ingredient ingredient) {
    return createModelWithId(ingredient.getId(), ingredient);
  }
  public CollectionModel<IngredientModel> toCollectionModel(Iterable<? extends Ingredient> entities) {
    return super.toCollectionModel(entities);
  }
}

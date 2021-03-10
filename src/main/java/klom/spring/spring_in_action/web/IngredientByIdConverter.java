package klom.spring.spring_in_action.web;

import java.util.Optional;
import klom.spring.spring_in_action.Ingredient;
import klom.spring.spring_in_action.data.IngredientRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

  private final IngredientRepository ingredientRepo;

  public IngredientByIdConverter(
      IngredientRepository ingredientRepo) {
    this.ingredientRepo = ingredientRepo;
  }

  @Override
  public Ingredient convert(String id) {
    Optional<Ingredient> optionalIngredient = ingredientRepo.findById(id);
    return optionalIngredient.isPresent() ? optionalIngredient.get() : null;
  }
}

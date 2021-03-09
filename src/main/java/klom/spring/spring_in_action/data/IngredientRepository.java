package klom.spring.spring_in_action.data;

import klom.spring.spring_in_action.Ingredient;

public interface IngredientRepository {

  Iterable<Ingredient> findAll();

  Ingredient findById(String id);

  Ingredient save(Ingredient ingredient);
}

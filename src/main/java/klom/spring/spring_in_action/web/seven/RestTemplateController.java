package klom.spring.spring_in_action.web.seven;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import klom.spring.spring_in_action.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@Slf4j
public class RestTemplateController {

  private RestTemplate rest = new RestTemplate();

  public Ingredient getIngredientById(String ingredientId) {
    return rest
        .getForObject("http://localhost:8080/ingredients/{id}", Ingredient.class, ingredientId);
  }

  public Ingredient getIngredientById2(String ingredientId) {
    Map<String, String> urlVariables = new HashMap<>();
    urlVariables.put("id", ingredientId);
    return rest
        .getForObject("http://localhost:8080/ingredients/{id}", Ingredient.class, ingredientId);
  }

  public Ingredient getIngredientById3(String ingredientId) {
    Map<String, String> urlVariables = new HashMap<>();
    urlVariables.put("id", ingredientId);
    URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/ingredients/{id}")
        .build(urlVariables);
    return rest
        .getForObject(url, Ingredient.class);
  }

  public Ingredient getIngredientById4(String ingredientId) {
    ResponseEntity<Ingredient> responseEntity = rest
        .getForEntity("http://localhost:8080/ingredients/{id}", Ingredient.class, ingredientId);
    log.info("Fetched time: " + responseEntity.getHeaders().getDate());
    return responseEntity.getBody();
  }

  public void updateIngredient(Ingredient ingredient) {
    rest
        .put("http://localhost:8080/ingredients/{id}", ingredient, ingredient.getId());
  }

  public void deleteIngredient(Ingredient ingredient) {
    rest
        .delete("http://localhost:8080/ingredients/{id}", ingredient.getId());
  }

  public Ingredient createIngredient(Ingredient ingredient) {
    return rest
        .postForObject("http://localhost:8080/ingredients", ingredient, Ingredient.class);

  }

  public URI createIngredient2(Ingredient ingredient) {
    return rest.postForLocation("http://localhost:8080/ingredients", ingredient);
  }

  public Ingredient createIngredient3(Ingredient ingredient) {
    ResponseEntity<Ingredient> responseEntity = rest
        .postForEntity("http://localhost:8080/ingredients", ingredient, Ingredient.class);
    log.info("New resource created at " + responseEntity.getHeaders().getLocation());
    return responseEntity.getBody();
  }


}

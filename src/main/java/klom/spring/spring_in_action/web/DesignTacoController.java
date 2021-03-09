package klom.spring.spring_in_action.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import klom.spring.spring_in_action.Ingredient;
import klom.spring.spring_in_action.Ingredient.Type;
import klom.spring.spring_in_action.Order;
import klom.spring.spring_in_action.Taco;
import klom.spring.spring_in_action.data.IngredientRepository;
import klom.spring.spring_in_action.data.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

  private final IngredientRepository ingredientRepository;
  private final TacoRepository tacoRepo;


  public DesignTacoController(
      IngredientRepository ingredientRepository, TacoRepository tacoRepo) {
    this.ingredientRepository = ingredientRepository;
    this.tacoRepo = tacoRepo;
  }

  @ModelAttribute(name = "order")
  public Order order() {
    return new Order();
  }

  @ModelAttribute(name = "taco")
  public Taco taco() {
    return new Taco();
  }

  @GetMapping
  public String showDesignForm(Model model) {
//    List<Ingredient> ingredients = Arrays.asList(
//        new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//        new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//        new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//        new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//        new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//        new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//        new Ingredient("CHED", "Cheddar", Type.CHEESE),
//        new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//        new Ingredient("SLSA", "Salsa", Type.SAUCE),
//        new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//    );

    List<Ingredient> ingredients = new ArrayList<>();
    ingredientRepository.findAll().forEach(i -> ingredients.add(i));
    Type[] types = Type.values();
    for (Type type : types) {
      model.addAttribute(type.toString().toLowerCase(),
          filterByType(ingredients, type));
    }
    model.addAttribute("taco", new Taco());
    return "design";
  }

  private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
    return ingredients
        .stream()
        .filter(x -> x.getType().equals(type))
        .collect(Collectors.toList());
  }

  @PostMapping
  public String processDesign(@Valid Taco design, Errors errors) {
    if (errors.hasErrors()) {
      return "design";
    }
    log.info("Processing design: " + design);
    return "redirect:/orders/current";
  }

}

package tacos.web;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.Errors;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import lombok.extern.slf4j.Slf4j;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;

import java.util.stream.Collectors;

@Slf4j
@Controller
@SessionAttributes("order")
@RequestMapping("/design")

public class DesignTacoController {
	private final IngredientRepository ingredientRepo;
	private TacoRepository designRepo;
	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo) {
	this.ingredientRepo = ingredientRepo;
	this.designRepo = designRepo;
	}
	/*
	 * Returns all the ingredients of different types to the design page
	 * */
	@GetMapping
	public String showDesignForm(Model model) {
	List<Ingredient> ingredients = new ArrayList<>();
	ingredientRepo.findAll().forEach(i -> ingredients.add(i));
	Type[] types = Ingredient.Type.values();
	for (Type type : types) {
	model.addAttribute(type.toString().toLowerCase(),
	filterByType(ingredients, type));
	}
	return "design";
	}
	
	  @ModelAttribute(name = "taco")
	  public Taco taco() {
	    return new Taco();
	  }
	  
	  @ModelAttribute(name = "order")
	  public Order order() {
	  return new Order();
	  }
/*
	@ModelAttribute
	public void addIngredientsToModel(Model model) {
List<Ingredient> ingredients = Arrays.asList(
new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
new Ingredient("CARN", "Carnitas", Type.PROTEIN),
new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
new Ingredient("LETC", "Lettuce", Type.VEGGIES),
new Ingredient("CHED", "Cheddar", Type.CHEESE),
new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
new Ingredient("SLSA", "Salsa", Type.SAUCE),
new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
);
Type[] types = Ingredient.Type.values();
for (Type type : types) {
model.addAttribute(type.toString().toLowerCase(),
filterByType(ingredients, type));
}
	}

	
@GetMapping
public String showDesignForm(Model model) {
  model.addAttribute("design", new Taco());
  return "design";
}
*/

private List<Ingredient> filterByType(
	      List<Ingredient> ingredients, Type type) {
	    return ingredients
	              .stream()
	              .filter(x -> x.getType().equals(type))
	              .collect(Collectors.toList());
	  }

@PostMapping
public String processDesign(@Valid @ModelAttribute("taco") Taco design, Errors errors, @ModelAttribute Order order) {
    if (errors.hasErrors()) {
      return "design";
    }
log.info("Processing design: " + design);

Taco saved = designRepo.save(design);
order.addDesign(saved);

return "redirect:/orders/current";
}
} 	
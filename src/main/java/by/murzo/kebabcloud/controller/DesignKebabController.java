package by.murzo.kebabcloud.controller;

import by.murzo.kebabcloud.entity.Ingredient;
import by.murzo.kebabcloud.entity.Kebab;
import by.murzo.kebabcloud.entity.KebabOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("kebabOrder")
public class DesignKebabController {

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("STPI", "Standard Pita", Ingredient.Type.WRAP),
                new Ingredient("CHPI", "Cheese Pita", Ingredient.Type.WRAP),
                new Ingredient("BEEF", "Beef", Ingredient.Type.MEAT),
                new Ingredient("PORK", "Pork", Ingredient.Type.MEAT),
                new Ingredient("CHEN", "Chicken", Ingredient.Type.MEAT),
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                new Ingredient("CBGE", "Cabbage", Ingredient.Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("HLND", "Holland", Ingredient.Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
        );

        Ingredient.Type[] types = Ingredient.Type.values();
        for(Ingredient.Type type: types) {
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "kebabOrder")
    public KebabOrder order() {
        return new KebabOrder();
    }
    @ModelAttribute(name = "kebab")
    public Kebab kebab() {
        return new Kebab();
    }
    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }

    @PostMapping
    public String processKebab(@Valid Kebab kebab, Errors errors, @ModelAttribute KebabOrder kebabOrder) {
        if(errors.hasErrors()) {
            return "design";
        }
        kebabOrder.addKebab(kebab);
        log.info("Processing kebab: {}", kebab);
        return "redirect:/orders/current";
    }
}

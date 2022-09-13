package by.murzo.kebabcloud.controller;

import by.murzo.kebabcloud.data.IngredientRepository;
import by.murzo.kebabcloud.model.Ingredient;
import by.murzo.kebabcloud.model.Kebab;
import by.murzo.kebabcloud.model.KebabOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
@RequestMapping(value = "/design")
@SessionAttributes("kebabOrder")
public class DesignKebabController {

    private final IngredientRepository ingredientRepo;

    @Autowired
    public DesignKebabController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepo.findAll();


        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
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

    private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Ingredient.Type type) {
        return StreamSupport.stream(ingredients.spliterator(), false).filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }

    @PostMapping
    public String processKebab(@Valid Kebab kebab, Errors errors, @ModelAttribute KebabOrder kebabOrder) {
        if (errors.hasErrors()) {
            return "design";
        }
        kebabOrder.addKebab(kebab);
        log.info("Processing kebab: {}", kebab);
        return "redirect:/orders/current";
    }
}

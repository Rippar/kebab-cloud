package by.murzo.kebabcloud.controller;

import by.murzo.kebabcloud.data.IngredientRepository;
import by.murzo.kebabcloud.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    IngredientRepository ingredientRepository;

    @Autowired
    public HomeController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/getIngrediens")
    public ResponseEntity<Iterable<Ingredient>> read() {
        Iterable<Ingredient> ingredients = ingredientRepository.findAll();
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

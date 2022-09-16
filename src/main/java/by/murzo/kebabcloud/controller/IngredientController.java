package by.murzo.kebabcloud.controller;

import by.murzo.kebabcloud.data.IngredientRepository;
import by.murzo.kebabcloud.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/ingredients", produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
public class IngredientController {

    private final IngredientRepository ingredientRepo;

    @Autowired
    public IngredientController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @GetMapping(params = "all")
    public Iterable<Ingredient> recentIngredients() {
//        PageRequest page = PageRequest.of(0,2);
        return ingredientRepo.findAll();
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Ingredient> ingredientById(@PathVariable("id") String id) {
        Optional<Ingredient> optIngredient = ingredientRepo.findById(id);
        if(optIngredient.isPresent()) {
            return new ResponseEntity<>(optIngredient.get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Ingredient postIngredient(@RequestBody Ingredient ingredient) {
        return ingredientRepo.save(ingredient);
    }

    //update all ingredient's fields
    @PutMapping(path ="/{ingredientId}", consumes = "application/json")
    public Ingredient putIngredient(@PathVariable("ingredientId") String ingredientId,
                                    @RequestBody Ingredient ingredient) {

        ingredient.setId(ingredientId);
        return ingredientRepo.save(ingredient);
    }

    @PatchMapping(path ="/{ingredientId}", consumes = "application/json")
    public Ingredient patchIngredient(@PathVariable("ingredientId") String ingredientId,
                                    @RequestBody Ingredient patch) {

        Ingredient ingredient = ingredientRepo.findById(ingredientId).get();
        if(patch.getName()!= null) {
            ingredient.setName(patch.getName());
        }

        if(patch.getType()!= null) {
            ingredient.setType(patch.getType());
        }

        return ingredientRepo.save(ingredient);

    }

    @DeleteMapping("/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable("ingredientId") String ingredientId) {
        try {
            ingredientRepo.deleteById(ingredientId);
        } catch (EmptyResultDataAccessException e) {

        }
    }




}

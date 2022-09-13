package by.murzo.kebabcloud.data;

import by.murzo.kebabcloud.model.Ingredient;
import org.springframework.data.repository.CrudRepository;


public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}

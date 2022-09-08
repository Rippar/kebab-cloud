package by.murzo.kebabcloud.web;

import by.murzo.kebabcloud.entity.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private Map<String, Ingredient> ingredientMap = new HashMap<>();

    public IngredientByIdConverter() {
        ingredientMap.put("STPI", new Ingredient("STPI", "Standard Pita", Ingredient.Type.WRAP));
        ingredientMap.put("CHPI", new Ingredient("CHPI", "Cheese Pita", Ingredient.Type.WRAP));
        ingredientMap.put("BEEF", new Ingredient("BEEF", "Beef", Ingredient.Type.MEAT));
        ingredientMap.put("PORK", new Ingredient("PORK", "Pork", Ingredient.Type.MEAT));
        ingredientMap.put("CHEN", new Ingredient("CHEN", "Chicken", Ingredient.Type.MEAT));
        ingredientMap.put("TMTO", new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
        ingredientMap.put("CBGE", new Ingredient("CBGE", "Cabbage", Ingredient.Type.VEGGIES));
        ingredientMap.put("CHED", new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
        ingredientMap.put("HLND", new Ingredient("HLND", "Holland", Ingredient.Type.CHEESE));
        ingredientMap.put("SLSA", new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
        ingredientMap.put("SRCR", new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));


    }

    @Override
    public Ingredient convert(String id) {
        return ingredientMap.get(id);
    }
}

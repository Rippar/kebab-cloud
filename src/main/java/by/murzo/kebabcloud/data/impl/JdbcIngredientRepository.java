package by.murzo.kebabcloud.data.impl;

import by.murzo.kebabcloud.data.IngredientRepository;
import by.murzo.kebabcloud.entity.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private static final String SQL_SELECT_ALL_INGREDIENTS = "SELECT id, name, type FROM Ingredient";
    private static final String SQL_SELECT_INGREDIENTS_BY_ID = "SELECT id, name, type FROM Ingredient WHERE id=?";
    private static final String SQL_INSERT_INGREDIENT = "INSERT INTO Ingredient (id, name, type) VALUES (?,?,?)";

    private JdbcTemplate jdbcTemplate;

    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL_INGREDIENTS, this::mapRowToIngredient);
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        List<Ingredient> results = jdbcTemplate.query(SQL_SELECT_INGREDIENTS_BY_ID, this::mapRowToIngredient, id);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update(SQL_INSERT_INGREDIENT, ingredient.getId(), ingredient.getName(),
                ingredient.getType().toString());
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
        return new Ingredient(row.getString("id"), row.getString("name"),
                Ingredient.Type.valueOf(row.getString("type")));
    }
}

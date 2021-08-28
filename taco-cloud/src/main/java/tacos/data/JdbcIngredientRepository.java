package tacos.data;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import tacos.Ingredient;

@Repository
public class JdbcIngredientRepository
implements IngredientRepository {
private JdbcTemplate jdbc;

/* @JdbcTemplate
 * This is the central class in the JDBC core package. 
 * It simplifies the use of JDBC and helps to avoid common errors. 
 * It executes core JDBC workflow, leaving application code to provide SQL and extract results
 * 
 */
@Autowired
public JdbcIngredientRepository(JdbcTemplate jdbc) {
this.jdbc = jdbc;
}
@Override
/*
 * Return Object of type Ingredients as list
 * 
 */
public Iterable<Ingredient> findAll() {
return jdbc.query("select id, name, type from Ingredient",
this::mapRowToIngredient);
}
/*
 * Return specified Object of type Ingredients 
 * 
 */
@Override
public Ingredient findOne(String id) {
return jdbc.queryForObject(
"select id, name, type from Ingredient where id=?",
this::mapRowToIngredient, id);
}
/*
 * Save an ingredient in DB
 * 
 */
@Override
public Ingredient save(Ingredient ingredient) {
jdbc.update(
"insert into Ingredient (id, name, type) values (?, ?, ?)",
ingredient.getId(),
ingredient.getName(),
ingredient.getType().toString());
return ingredient;
}
/*
 * Create object from resultSet and return 
 * For ingredient type - ENUM value is picked using valueOf 
 */
private Ingredient mapRowToIngredient(ResultSet rs, int rowNum)
throws SQLException {
return new Ingredient(
rs.getString("id"),
rs.getString("name"),
Ingredient.Type.valueOf(rs.getString("type")));
}
}
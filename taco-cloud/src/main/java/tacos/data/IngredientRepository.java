package tacos.data;
import tacos.Ingredient;
/*
 *  Ingredient interface for individual ingredient operations
 * 
 */
public interface IngredientRepository {
Iterable<Ingredient> findAll();
Ingredient findOne(String id);
Ingredient save(Ingredient ingredient);
}
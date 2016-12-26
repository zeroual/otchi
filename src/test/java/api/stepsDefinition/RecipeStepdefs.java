package api.stepsDefinition;

import com.otchi.application.PublicationsService;
import com.otchi.domain.kitchen.Ingredient;
import com.otchi.domain.kitchen.Instruction;
import com.otchi.domain.kitchen.Recipe;
import com.otchi.domain.kitchen.RecipeRepository;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Collections.emptyList;

@Transactional
public class RecipeStepdefs {

    @Autowired
    private PublicationsService publicationsService;

    @Autowired
    private RecipeRepository recipeRepository;

    @And("^those recipes$")
    public void thoseRecipes(DataTable dataTable) throws Throwable {
        dataTable.asList(RecipeCucumber.class).forEach(
                recipeCucumber -> {
                    String author = recipeCucumber.getAuthor();
                    Recipe recipe = recipeCucumber.getRecipe();
                    publicationsService.publishRecipe(recipe, emptyList(), author);
                }
        );
    }

    @And("^those recipe ingredients$")
    public void thoseIngredients(DataTable dataTable) throws Throwable {
        dataTable.asList(CucumberIngredient.class).forEach(
                cucumberIngredient -> {
                    Long recipeId = cucumberIngredient.getRecipeId();
                    Recipe recipe = recipeRepository.findOne(recipeId);
                    Ingredient ingredient = cucumberIngredient.getIngredient();
                    recipe.addIngredient(ingredient);
                    recipeRepository.save(recipe);
                }
        );
    }

    @And("^those recipe instructions$")
    public void thoseRecipeInstructions(DataTable dataTable) throws Throwable {
        dataTable.asList(CucumberInstruction.class).forEach(
                cucumberInstruction -> {
                    Long recipeId = cucumberInstruction.getRecipeId();
                    Recipe recipe = recipeRepository.findOne(recipeId);
                    Instruction instruction = cucumberInstruction.getInstruction();
                    recipe.addInstruction(instruction);
                    recipeRepository.save(recipe);
                }
        );
    }

    private class RecipeCucumber {
        private String title;
        private String description;
        private Integer cookTime;
        private Integer preparationTime;
        private String author;

        public String getAuthor() {
            return author;
        }

        public Recipe getRecipe() {
            return new Recipe(title, description, cookTime, preparationTime);
        }
    }

    private class CucumberIngredient {
        private String name;
        private String unit;
        private Double quantity;
        private Long recipeId;

        public Ingredient getIngredient() {
            return new Ingredient(name, quantity, unit);
        }

        public Long getRecipeId() {
            return recipeId;
        }
    }

    private class CucumberInstruction {
        private Long recipeId;
        private String instruction;

        public Long getRecipeId() {
            return recipeId;
        }

        public Instruction getInstruction() {
            return new Instruction(instruction);
        }
    }
}

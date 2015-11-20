/**
 * Created by Abdellah on 20/11/2015.
 */

angular.module("kitchen")
    .service("RecipeService", function ($resource) {
        var service=$resource('/rest/v1/recipe');
        this.createRecipe = function (recipe) {
            return service.save(recipe);
        };

        this.fetchAllRecipes = function () {
            return service.query();
        };
    });
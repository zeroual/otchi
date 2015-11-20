/**
 * Created by Abdellah on 20/11/2015.
 */

angular.module("kitchen").
    controller("RecipeController", function ($scope, RecipeService) {
        $scope.recipesList = RecipeService.fetchAllRecipes();
        $scope.addRecipe = function () {
            RecipeService.createRecipe($scope.recipe);
            $scope.recipesList.push($scope.recipe);
            $scope.recipe = {};
        }
    });
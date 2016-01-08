
angular.module("kitchen").
controller("RecipeController", function ($scope, RecipeService) {

    $scope.recipe = {ingredients: [], instructions: []};

    $scope.recipesList = RecipeService.fetchAllRecipes();
    $scope.createRecipe = function () {
        RecipeService.createRecipe($scope.recipe);
        $scope.recipesList.push($scope.recipe);
        $scope.recipe = {};
    };

    $scope.addIngredient = function () {
        $scope.recipe.ingredients.push({});
    };

    $scope.removeIngredient = function (index) {
        $scope.recipe.ingredients.splice(index, 1);
    };

    $scope.addInstruction = function () {
        $scope.recipe.instructions.push({})
    };

    $scope.removeInstruction = function (index) {
        $scope.recipe.instructions.splice(index, 1);
    };
});
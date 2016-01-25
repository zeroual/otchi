angular.module("publisher")
    .directive('recipePublisher', function () {
        return {
            templateUrl: 'app/social/publisher/views/recipe-publisher-directive.html',
            controller: function ($scope, PostService) {

                $scope.recipe = {ingredients: [], instructions: []};

                $scope.$on('SHARE_RECIPE_EVENT', function () {
                    $scope.shareRecipe();
                });

                $scope.shareRecipe = function () {
                    PostService.publishRecipe($scope.recipe);
                    $scope.recipe = {};
                };

                $scope.publishRecipe = function () {
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
            }
        };
    });
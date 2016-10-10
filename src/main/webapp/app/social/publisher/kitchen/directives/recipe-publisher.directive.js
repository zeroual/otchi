angular.module("publisher")
    .directive('recipePublisher', function () {
        return {
            templateUrl: 'app/social/publisher/kitchen/directives/recipe-publisher-directive.html',
            controller: function ($scope, $rootScope, ShareService) {

                function init() {
                    $scope.recipe = {ingredients: [], instructions: []};
                }

                init();

                $scope.shareRecipe = function () {
                    ShareService.publishRecipe($scope.recipe).then(function (data) {
                        $rootScope.$broadcast('NEW_POST_PUBLISHED_EVENT', data);
                    });
                    init();
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

                $scope.addTag = function (index){
                    $scope.recipe.tags.push({})
                };

                $scope.removeTag = function (index) {
                    $scope.recipe.tags.splice(index, 1);
                };
            }
        };
    });
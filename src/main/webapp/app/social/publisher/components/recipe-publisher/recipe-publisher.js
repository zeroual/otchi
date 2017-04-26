angular.module("publisher")
    .directive('recipePublisher', function () {
        return {
            templateUrl: 'app/social/publisher/components/recipe-publisher/recipe-publisher.html',
            controller: function ($scope, $rootScope, ShareService, ToasterService, $state) {

                function init() {
                    $scope.recipe = {
                        ingredients: [{}],
                        instructions: [{}],
                        pictures: [],
                        tags: []
                    };
                    $scope.tags = [];
                }

                init();


                extractTags = function(){
                    return $scope.tags.map(function(tag){
                        return tag.text;
                    });
                };

                $scope.shareRecipe = function () {
                $scope.recipe.tags = extractTags();
                    ShareService.publishRecipe($scope.recipe).then(function (feed) {
                        $state.go('showRecipe', {feedId: feed.id});
                    }).catch(function () {
                        ToasterService.error('post.recipe.failed');
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

                $scope.deleteImage = function (image) {
                    var index = $scope.recipe.pictures.indexOf(image);
                    $scope.recipe.pictures.splice(index, 1);
                };
            }
        };
    });

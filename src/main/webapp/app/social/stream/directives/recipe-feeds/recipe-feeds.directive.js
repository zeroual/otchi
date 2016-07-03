angular.module("stream")
    .directive('recipeFeeds', function () {
        return {
            scope: {
                recipe: '='
            },
            controller:function($http, $scope){
                $http.get("/rest/v1/recipe-feeds").then(function(response){
                return response.data;
                }).then(function(recipes){
                    $scope.recipes  = recipes;
                })
            },
            templateUrl: 'app/social/stream/directives/recipe-feeds/recipe-feeds.template.html'
        };
    });
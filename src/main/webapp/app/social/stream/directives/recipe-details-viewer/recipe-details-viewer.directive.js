angular.module("stream")
    .directive('recipeDetailsViewer', function () {
        return {
            scope: {
                feed: '='
            },
            controller: function ($scope) {
                $scope.recipe = $scope.feed.content;
            },
            templateUrl: 'app/social/stream/directives/recipe-details-viewer/recipe-details-viewer.template.html'
        };
    });

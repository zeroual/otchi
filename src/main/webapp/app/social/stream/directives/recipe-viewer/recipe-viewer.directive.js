//TODO add render test for this directive
angular.module("stream")
    .directive('recipeViewer', function () {
        return {
            scope: {
                recipe: '=',
                feedId: '='
            },
            templateUrl: 'app/social/stream/directives/recipe-viewer/recipe-viewer.template.html'
        };
    });
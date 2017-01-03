//TODO add render test for this directive
angular.module("stream")
    .directive('recipeViewer', function () {
        return {
            scope: {
                recipe: '=',
                feedId: '=',
                images: '='
            },
            templateUrl: 'app/social/stream/directives/recipe-viewer/recipe-viewer.template.html'
        };
    });
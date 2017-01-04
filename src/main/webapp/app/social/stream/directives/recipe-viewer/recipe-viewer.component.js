//TODO add render test for this directive
angular.module("stream")
    .component('recipeViewer', {
        bindings: {
            recipe: '<',
            feedId: '<',
            images: '<'
        },
        templateUrl: 'app/social/stream/directives/recipe-viewer/recipe-viewer.template.html'
    });
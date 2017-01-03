angular.module("stream")
    .directive('storyViewer', function () {
        return {
            scope: {
                story: '=',
                images: '='
            },
            templateUrl: 'app/social/stream/directives/story-viewer/story-viewer.template.html'
        };
    });
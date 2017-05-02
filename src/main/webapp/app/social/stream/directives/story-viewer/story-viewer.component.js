angular.module("stream")
    .component('storyViewer', {
        bindings: {
            story: '<'
        },
        templateUrl: 'app/social/stream/directives/story-viewer/story-viewer.template.html'
    });
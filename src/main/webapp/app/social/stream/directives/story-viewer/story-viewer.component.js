angular.module("stream")
    .component('storyViewer', {
        bindings: {
            story: '<',
            images: '<'
        },
        templateUrl: 'app/social/stream/directives/story-viewer/story-viewer.template.html'
    });
angular.module('publisher')
    .component('storyPublisher', {
        templateUrl: 'app/social/publisher/components/story-publisher/story-publisher.html',
        controller: function (ShareService, $state) {
            var ctrl = this;

            ctrl.story = {
                content: '',
                images: []
            };

            ctrl.shareStory = function () {
                ShareService.publisherStory(ctrl.story).then(function () {
                    $state.go('feed');
                });
            };

            ctrl.deleteImage = function (image) {
                var index = ctrl.story.images.indexOf(image);
                ctrl.story.images.splice(index, 1);
            };
        }
    });

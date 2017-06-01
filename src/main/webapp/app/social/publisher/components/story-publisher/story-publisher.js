angular.module('publisher')
    .component('storyPublisher', {
        templateUrl: 'app/social/publisher/components/story-publisher/story-publisher.html',
        controller: function (ShareService, $state, localStorageService, ImageBase64Encoder) {
            var ctrl = this;
            ctrl.images = [];

            function loadStoryFromLocalStorage() {
                var story = localStorageService.get('story');
                if (story != undefined) {
                    ctrl.story = story;
                } else {
                    ctrl.story = {
                        content: ''
                    };
                }
            }

            function loadStoryImagesFromLocalStorage() {
                var imagesBase64 = localStorageService.get('story-images');
                if (imagesBase64 != undefined) {
                    imagesBase64.forEach(function (imageBase64) {
                        var image = ImageBase64Encoder.decode(imageBase64);
                        ctrl.images.push(image);
                    });
                }
            }

            ctrl.$onInit = function () {
                loadStoryFromLocalStorage();
                loadStoryImagesFromLocalStorage();
            };

            ctrl.shareStory = function () {
                ctrl.story.images = ctrl.images;
                ShareService.publisherStory(ctrl.story).then(function () {
                    ctrl.story = {};
                    ctrl.images = [];
                    $state.go('feed');
                });
            };

            ctrl.$onDestroy = function () {
                localStorageService.set('story', ctrl.story);
                localStorageService.set('story-images', []);
                ImageBase64Encoder.encode(ctrl.images).then(function (imagesBase64) {
                    localStorageService.set('story-images', imagesBase64);
                });
            };

            ctrl.deleteImage = function (image) {
                var index = ctrl.images.indexOf(image);
                ctrl.images.splice(index, 1);
            };

        }
    });

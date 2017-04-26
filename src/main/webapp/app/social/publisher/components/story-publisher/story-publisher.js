angular.module('publisher')
    .directive('storyPublisher', function ($rootScope, ShareService) {
        return {
            restrict: 'E',
            templateUrl: 'app/social/publisher/components/story-publisher/story-publisher.html',
            controller: function ($scope) {

                function init() {
                    $scope.story = {
                        content: '',
                        images: []
                    };
                }

                init();

                $scope.shareStory = function () {
                    ShareService.publisherStory($scope.story).then(function (createdPost) {
                        init();
                    });
                };

                $scope.deleteImage = function (image) {
                    var index = $scope.story.images.indexOf(image);
                    $scope.story.images.splice(index, 1);
                };
            }
        };
    });

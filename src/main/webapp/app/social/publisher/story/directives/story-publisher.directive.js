angular.module('publisher')
    .directive('storyPublisher', function ($rootScope, ShareService) {
        return {
            restrict: 'E',
            templateUrl: 'app/social/publisher/story/directives/story-publisher-template.html',
            controller: function ($scope) {

                function init() {
                    $scope.story = {
                        content: ''
                    };
                }

                init();

                $scope.shareStory = function () {
                    ShareService.publisherStory($scope.story).then(function (createdPost) {
                        init();
                        $rootScope.$broadcast('NEW_POST_PUBLISHED_EVENT', createdPost);
                    });
                };
            }
        };
    });

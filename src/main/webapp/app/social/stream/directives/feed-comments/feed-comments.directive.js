angular.module("stream")
    .directive('feedComments', function () {
        return {
            templateUrl: 'app/social/stream/directives/feed-comments/feed-comments.template.html',
            controller: function ($scope, FeedsService) {

                $scope.commentOnPost = function (feed) {
                    FeedsService.commentOnPost(feed, $scope.commentContent).then(function () {
                        $scope.commentContent = '';
                    });
                };
            }
        };
    });
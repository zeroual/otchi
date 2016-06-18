angular.module("stream")
    .directive('feedViewer', function () {
        return {
            templateUrl: 'app/social/stream/directives/feed-viewer/feed-viewer.template.html',
            controller: function ($scope, FeedsService, $uibModal) {

                $scope.likePost = function (post) {
                    FeedsService.likePost(post);
                };

                $scope.unLikePost = function (post) {
                    FeedsService.unLikePost(post);

                };

                $scope.showLikes = function (feed) {
                    $uibModal.open({
                        animation: true,
                        templateUrl: 'app/social/stream/views/feed-likes-modal.html',
                        controller: 'FeedLikesViewerController',
                        resolve: {
                            likes: function () {
                                return feed.likes;
                            }
                        }
                    });
                };

            }
        };
    });
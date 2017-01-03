angular.module("stream")
    .directive('feedViewer', function () {
        return {
            scope: {
                feed: "="
            },
            templateUrl: 'app/social/stream/directives/feed-viewer/feed-viewer.template.html',
            controller: function ($scope, $rootScope, FeedsService, $uibModal) {

                $scope.likePost = function (post) {
                    FeedsService.likePost(post);
                };

                $scope.unLikePost = function (post) {
                    FeedsService.unLikePost(post);

                };

                $scope.deletePost = function (post) {
                    $uibModal.open({
                        animation: true,
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: '/app/components/modal/confirm-delete-template.html',
                        size: 'sm',
                        controller: function ($scope, $uibModalInstance) {

                            $scope.ok = function () {
                                $rootScope.$broadcast('REMOVE_POST_PUBLISHED_EVENT', post);
                                $uibModalInstance.dismiss('ok');
                            };

                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            }
                        }
                    });
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
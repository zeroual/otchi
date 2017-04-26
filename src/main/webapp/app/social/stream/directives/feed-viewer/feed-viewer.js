angular.module("stream")
    .component('feedViewer', {
        bindings: {
            feed: "<",
            onRemove: '&'
        },
        templateUrl: 'app/social/stream/directives/feed-viewer/feed-viewer.html',
        controller: function (FeedsService, $uibModal) {
            var ctrl = this;

            ctrl.like = function () {
                FeedsService.likeFeed(ctrl.feed);
            };

            ctrl.unLike = function () {
                FeedsService.unLikeFeed(ctrl.feed);
            };

            ctrl.remove = function () {
                $uibModal.open({
                    animation: true,
                    ariaLabelledBy: 'modal-title',
                    ariaDescribedBy: 'modal-body',
                    templateUrl: '/app/components/modal/confirm-delete-template.html',
                    size: 'sm',
                    controller: function ($scope, $uibModalInstance) {

                        $scope.ok = function () {
                            ctrl.onRemove(ctrl.feed);
                            $uibModalInstance.dismiss('ok');
                        };

                        $scope.cancel = function () {
                            $uibModalInstance.dismiss('cancel');
                        }
                    }
                });
            };

            ctrl.showLikes = function () {
                $uibModal.open({
                    animation: true,
                    templateUrl: 'app/social/stream/views/feed-likes-modal.html',
                    controller: 'FeedLikesViewerController',
                    resolve: {
                        likes: function () {
                            return ctrl.feed.likes;
                        }
                    }
                });
            };
        }
    });

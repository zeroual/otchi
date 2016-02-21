angular.module("stream")
    .controller("FeedsLoaderController", function ($scope, $rootScope, $uibModal, FeedsService) {
        $scope.feeds = FeedsService.fetchAllFeeds();

        $scope.likePost = function (post){
            FeedsService.likePost(post);

        };
        $scope.unLikePost = function (post){
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

        //FIXME CHANGE the name of this method
        $scope.loadNewPublishedPost = function (feed) {
            feed.likes = [];
            feed.liked = false;
            $scope.feeds.unshift(feed);
        };

        $rootScope.$on('NEW_POST_PUBLISHED_EVENT', function (event, feed) {
            $scope.loadNewPublishedPost(feed);
        });
    });
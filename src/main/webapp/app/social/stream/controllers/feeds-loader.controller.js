angular.module("stream")
    .controller("FeedsLoaderController", function ($scope, $rootScope, FeedsService) {
        $scope.feeds = FeedsService.fetchAllFeeds();

        $scope.likePost = function (post){
            FeedsService.likePost(post);

        };
        $scope.unLikePost = function (post){
            FeedsService.unLikePost(post);

        };
        //FIXME CHANGE the name of this method
        $scope.loadNewPublishedPost = function (feed) {
            $scope.feeds.unshift(feed);
        };

        $rootScope.$on('NEW_POST_PUBLISHED_EVENT', function (event, feed) {
            $scope.loadNewPublishedPost(feed);
        });
    });
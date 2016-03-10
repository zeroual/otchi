angular.module("stream")
    .controller("FeedsLoaderController", function ($scope, $rootScope, FeedsService) {
        $scope.feeds = FeedsService.fetchAllFeeds();

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
angular.module("stream")
    .controller("FeedsLoaderController", function ($scope, FeedsService) {
        $scope.feeds = FeedsService.fetchAllFeeds();

        //FIXME CHANGE the name of this method
        $scope.loadNewPublishedPost = function (feed) {
            $scope.feeds.unshift(feed);
        };

        $scope.$on('NEW_POST_PUBLISHED_EVENT', function (event, feed) {
            $scope.loadNewPublishedPost(feed);
        });
    });
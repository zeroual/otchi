angular.module("stream")
    .controller("FeedsLoaderController", function ($scope, FeedsService) {
        $scope.feeds = FeedsService.fetchAllFeeds();
    });
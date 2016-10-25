//TODO remove this controller
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

        $scope.deletePost = function (feed) {
        	FeedsService.deletePost(feed);
        	var index = $scope.feeds.map(function(post) {
                return post.id;
            }).indexOf(feed.id)
            $scope.feeds.splice(index, 1);
        };

        $rootScope.$on('REMOVE_POST_PUBLISHED_EVENT', function (event, feed) {
            $scope.deletePost({'id': feed.id});
        });
    });
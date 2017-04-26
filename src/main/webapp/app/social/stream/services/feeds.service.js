angular.module("stream")
    .service("FeedsService", function ($resource) {

        var Feed = $resource('/rest/v1/feed/:id', {id: '@id'},
            {
                like: {
                    method: 'POST',
                    url: '/rest/v1/feed/:id/like'
                },
                unLike: {
                    method: 'POST',
                    url: '/rest/v1/feed/:id/unlike'
                },
                remove: {
                    method: 'DELETE',
                    url: '/rest/v1/feed/:id'
                },
                comment: {
                    method: 'POST',
                    url: '/rest/v1/feed/:id/comment'
                }
            }
        );

        this.fetchAllFeeds = function () {
            return Feed.query();
        };

        this.fetchFeed = function (feedId) {
            return Feed.get({id: feedId})
        };

        this.likeFeed = function (feed) {
            var param = {'id': feed.id};
            Feed.like(param, function () {
                feed.liked = true;
                feed.likesCount++;

            });
        };


        this.unLikeFeed = function (feed) {
            var param = {'id': feed.id};
            Feed.unLike(param, function () {
                feed.liked = false;
                feed.likesCount--;
            });

        };

        this.removeFeed = function (feed) {
            return Feed.remove(feed).$promise;
        };

        this.commentOnFeed = function (feed, comment) {
            return Feed.comment({'id': feed.id}, comment).$promise
                .then(function (response) {
                    feed.comments.push(response);
                });
        };

    });

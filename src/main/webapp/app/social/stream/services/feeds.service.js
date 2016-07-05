angular.module("stream")
    .service("FeedsService", function ($resource, Principal) {

        function _markFeedAsLikedOrNo(feed) {
            Principal.identity().then(function (account) {
                feed.liked = feed.likes.some(function (like) {
                    return account.id == like.id;
                });
            });
            return feed;
        };

        function _addMyLikeTo(post) {
            Principal.identity().then(function (account) {
                post.likes.push(account);
            });
        }

        function _removeMyLikeFrom(post) {
            Principal.identity().then(function (account) {
                post.likes.splice(post.likes.indexOf(function (like) {
                    return like === account.id;
                }), 1);
            });
        }

        var service = $resource('/rest/v1/feed/:id', {id: '@id'},
            {
                like: {
                    method: 'POST',
                    url: '/rest/v1/feed/:id/like'
                },
                unLike: {
                    method: 'POST',
                    url: '/rest/v1/feed/:id/unlike'
                },
                comment: {
                    method: 'POST',
                    url: '/rest/v1/feed/:id/comment'
                }
            }
        );

        this.fetchAllFeeds = function () {
            return service.query(function (feeds) {
                return feeds.map(_markFeedAsLikedOrNo);
            });
        };
        this.fetchFeed = function (feedId) {
            return service.get({id: feedId})
        };

        this.likePost = function (post) {
            service.like({'id': post.id});
            _addMyLikeTo(post);
            post.liked = true;
        };


        this.unLikePost = function (post) {
            service.unLike({'id': post.id});
            _removeMyLikeFrom(post);
            post.liked = false;
        };

        this.commentOnPost = function (feed, comment) {
            return service.comment({'id': feed.id}, comment).$promise
                .then(function (response) {
                    feed.comments.push(response);
                });
        };

    });
angular.module("stream")
    .service("FeedsService", function ($resource) {
        var service = $resource('/rest/v1/feed/:id', {id: '@id'},
            {
                like: {
                    method: 'POST',
                    url: '/rest/v1/feed/:id/like'
                },
                unLike: {
                    method: 'POST',
                    url: '/rest/v1/feed/:id/unlike'
                }
            }
        );

        this.fetchAllFeeds = function () {
            return service.query();
        };

        this.likePost = function (post) {
            service.like({'id': post.id});
            if (post.liked == undefined || post.liked == false) {
                post.likes++;
                post.liked = true;
            }
        };

        this.unLikePost = function (post) {
            service.unLike({'id': post.id});
            if (post.liked !=undefined && post.liked == true) {
                post.likes--;
                post.liked = false;
            }
        };

    });
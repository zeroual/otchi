angular.module("stream")
    .service("FollowingService", function ($resource) {
        var service = $resource('/rest/v1/me/following/', {},
            {
                follow: {
                    method: 'POST',
                    url: '/rest/v1/me/following'
                }
            });

        this.follow = function (user) {
            return service.follow(user.id).$promise;
        };

    });
angular.module("stream")
    .service("FollowingsRecommendationService", function ($resource) {

        var resource = $resource('/rest/v1/recommendations/followings');

        this.fetchAll = function () {
            return resource.query();
        };

    });
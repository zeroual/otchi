angular.module("stream")
    .service("FeedsService", function ($resource) {
        var service = $resource('/rest/v1/feed');

        this.fetchAllFeeds = function () {
            return service.query();
        };
    });
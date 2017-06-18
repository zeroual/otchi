angular.module("stream")
    .service("AnalyticsService", function ($resource) {

        var resource = $resource('/rest/v1/analytics/views/feed/:id', {id: '@id'});

        this.incrementFeedViews = function (feed) {
            var param = {'id': feed.id};
            resource.save(param, function () {
                feed.views++;
            });
        };
    });

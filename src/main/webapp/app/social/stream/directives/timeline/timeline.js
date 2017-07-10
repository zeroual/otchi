angular.module("stream")
    .component('timeline', {
        templateUrl: 'app/social/stream/directives/timeline/timeline.html',
        controller: function (FeedsService) {
            this.feeds = FeedsService.fetchAllFeeds();
        }
    });

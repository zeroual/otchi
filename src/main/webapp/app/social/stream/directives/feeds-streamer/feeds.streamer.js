angular.module("stream")
    .component('feedsStreamer', {
        templateUrl: 'app/social/stream/directives/feeds-streamer/feeds.streamer.html',
        controller: function (FeedsService) {

            var ctrl = this;
            ctrl.feeds = FeedsService.fetchAllFeeds();

            ctrl.removeFeed = function (feed) {
                var removeFeedFromStream = function () {
                    //TODO add this function as array extension
                    var index = ctrl.feeds.map(function (post) {
                        return post.id;
                    }).indexOf(feed.id);

                    ctrl.feeds.splice(index, 1);
                };
                FeedsService.removeFeed(feed).then(removeFeedFromStream);
            };
        }
    });

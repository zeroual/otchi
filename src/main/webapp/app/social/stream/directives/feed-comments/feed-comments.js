angular.module("stream")
    .component('feedComments', {
        bindings: {
            feed: '<'
        },
        templateUrl: 'app/social/stream/directives/feed-comments/feed-comments.html',
        controller: function (FeedsService) {
            var ctrl = this;

            ctrl.commentOnFeed = function () {
                FeedsService.commentOnFeed(ctrl.feed, ctrl.commentContent).then(function () {
                    ctrl.commentContent = '';
                });
            };
        }
    });
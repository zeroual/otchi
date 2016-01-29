angular.module("helpers")
    .filter('timeAgo', function () {
        return function (date) {
            return moment(date).fromNow();
        };
    });
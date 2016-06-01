angular.module("helpers")
    .filter('timeAgo', function () {
        return function (date) {
            var utc = moment.utc(date);
            return moment(utc.local()).fromNow()
        };
    });

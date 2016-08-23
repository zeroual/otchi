angular.module("stream")
    .service("NotificationsService", function ($resource) {
        var resource = $resource('/rest/v1/me/notifications');

        this.getUnreadNotifications = function () {
            return resource.query({unread: true}).$promise.then(function (notifications) {
                return notifications;
            });
        }
    });
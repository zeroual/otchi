angular.module("stream")
    .service("NotificationsService", function ($resource) {

        var resource = $resource('/rest/v1/me/notifications/:id', {id: '@id'}, {
            'update': {method: 'PUT'}
        });

        this.getUnreadNotifications = function () {
            return resource.query({unread: true}).$promise.then(function (notifications) {
                return notifications;
            });
        };

        this.markNotificationAsRead = function (id) {
            return resource.update({id: id}, {unread: false}).$promise;
        };
    });
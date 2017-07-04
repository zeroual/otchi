angular.module('otchi').component('navbarAuthenticated', {

    templateUrl: 'app/components/navbar/navbar-authenticated.html',
    controller: function (Principal, NotificationsService, Auth, $state) {
        var ctrl = this;
        Principal.identity().then(function (account) {
            ctrl.account = account;
        });

        NotificationsService.getUnreadNotifications().then(function (notifications) {
            ctrl.notifications = notifications;
        });

        ctrl.readNotification = function (notification) {
            NotificationsService.markNotificationAsRead(notification.id).then(function () {
                var index = ctrl.notifications.indexOf(notification);
                ctrl.notifications.splice(index, 1);
                $state.go('showPost', {postId: notification.postId});
            });
        };

        ctrl.logout = function () {
            Auth.logout();
            $state.go('index');
        };
    }
});
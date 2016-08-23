angular.module('otchi')
    .controller('NavBarController', function ($scope, $state, Auth, Principal, NotificationsService) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;

        NotificationsService.getUnreadNotifications().then(function (notifications) {
            $scope.notifications = notifications;
        });

        Principal.identity().then(function (account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.logout = function () {
            Auth.logout();
            $state.go('index');
        };
    });
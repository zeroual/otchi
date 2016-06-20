angular.module('authentication')
    .controller('LoginController', function ($rootScope, $scope, $state, Auth) {
        $scope.user = {};
        $scope.errors = {};

        $scope.rememberMe = true;
        $scope.login = function () {
            Auth.login({
                email: $scope.email,
                password: $scope.password,
                rememberMe: $scope.rememberMe
            }).then(function () {
                $scope.authenticationError = false;
                if ($rootScope.previousStateName == undefined || $rootScope.previousStateName == 'index') {
                    $state.go('feed');
                } else {
                    $rootScope.back();
                }
            }).catch(function () {
                $scope.authenticationError = true;
            });
        };
    });
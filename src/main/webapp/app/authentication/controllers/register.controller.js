angular.module('authentication')
    .controller('RegisterController', function ($scope, $translate, AccountService, Auth, $state) {
        $scope.error = null;
        $scope.account = {};

        $scope.register = function () {
            $scope.account.langKey = $translate.use();
            $scope.error = null;
            $scope.errorEmailExists = null;

            AccountService.register($scope.account).then(function () {
                Auth.login({
                    email: $scope.account.email,
                    password: $scope.account.password,
                    rememberMe: false
                }).then(function () {
                    $state.go('feed');
                }).catch(function () {
                    $state.go('login');
                });
            }).catch(function (response) {
                if (response.status === 409) {
                    $scope.errorEmailExists = 'ERROR';
                } else {
                    $scope.error = 'ERROR';
                }
            });

        };
    });
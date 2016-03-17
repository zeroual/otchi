'use strict';

angular.module('socialAuthentication')
    .controller('SocialRegisterController', function ($scope, $filter, $stateParams) {
        $scope.provider = $stateParams.provider;
        $scope.success = $stateParams.success;
        $scope.error = $stateParams.error;
    });

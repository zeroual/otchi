'use strict';

angular.module('authentication')
    .factory('Auth', function Auth($rootScope, $state, $q, Principal, AuthServerProvider) {
        return {
            login: function (credentials) {
                var deferred = $q.defer();
                AuthServerProvider.login(credentials).then(function (data) {
                    Principal.identity(true).then(function () {
                        deferred.resolve(data);
                    });
                }).catch(function (err) {
                    this.logout();
                    deferred.reject(err);
                }.bind(this));
                return deferred.promise;
            },
            logout: function () {
                AuthServerProvider.logout();
                Principal.authenticate(null);
                $rootScope.previousStateName = undefined;
                $rootScope.previousStateNameParams = undefined;
            }
        };
    });

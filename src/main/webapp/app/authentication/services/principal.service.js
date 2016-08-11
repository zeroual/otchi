angular.module('authentication')
    .factory('Principal', function Principal($q, IdentityService, SocketService, NotificationsHandlerService) {
        var _identity,
            _authenticated = false;

        return {
            isIdentityResolved: function () {
                return angular.isDefined(_identity);
            },
            isAuthenticated: function () {
                return _authenticated;
            },
            authenticate: function (identity) {
                _identity = identity;
                _authenticated = identity !== null;
            },
            identity: function (force) {
                var deferred = $q.defer();
                if (force === true) {
                    _identity = undefined;
                }
                // check and see if we have retrieved the identity data from the server.
                // if we have, reuse it by immediately resolving
                if (angular.isDefined(_identity)) {
                    deferred.resolve(_identity);
                    return deferred.promise;
                }
                // retrieve the identity data from the server, update the identity object, and then resolve.
                IdentityService.get().$promise
                    .then(function (account) {
                        _identity = account.data;
                        _authenticated = true;
                        deferred.resolve(_identity);
                        SocketService.connect().then(function () {
                            NotificationsHandlerService.handleNotifications();
                        });
                    })
                    .catch(function () {
                        _identity = null;
                        _authenticated = false;
                        deferred.resolve(_identity);
                    });
                return deferred.promise;
            }
        };
    });
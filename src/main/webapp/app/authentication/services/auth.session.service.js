angular.module('authentication')
    .factory('AuthServerProvider', function loginService($http, LOGIN_URL, LOGOUT_URL, ACCOUNT_URL) {
        return {
            login: function (credentials) {
                var data = 'email=' + encodeURIComponent(credentials.email) +
                    '&password=' + encodeURIComponent(credentials.password) +
                    '&remember-me=' + credentials.rememberMe + '&submit=Login';
                return $http.post(LOGIN_URL, data, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).success(function (response) {
                    return response;
                });
            },
            logout: function () {
                // logout from the server
                $http.post(LOGOUT_URL).success(function (response) {
                    // to get a new csrf token call the api
                    $http.get(ACCOUNT_URL);
                    return response;
                });
            }
        };
    });

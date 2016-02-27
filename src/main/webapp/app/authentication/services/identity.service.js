angular.module('authentication')
    .factory('IdentityService', function Account($resource, ACCOUNT_URL) {
        return $resource(ACCOUNT_URL, {}, {
            'get': {
                method: 'GET', params: {}, isArray: false,
                interceptor: {
                    response: function (response) {
                        return response;
                    }
                }
            }
        });
    });
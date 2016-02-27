angular.module('authentication')
    .factory('AccountService', function Account($resource) {

        var account_url = '/rest/v1/account';
        var accountResource = $resource(account_url, {}, {
            register: {
                method: 'POST',
                url: account_url + '/register'
            }
        });

        return {
            register: function (account) {
                return accountResource.register(account).$promise;
            }
        };
    });
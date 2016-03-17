'use strict';

angular.module('socialAuthentication')
    .directive('socialAuthButton', function ($translate, $filter, SocialAuthenticationService) {
        return {
            restrict: 'E',
            scope: {
                provider: "@ngProvider"
            },
            templateUrl: 'app/social-authentication/views/social-auth-button.html',
            link: function (scope) {
                scope.providerSetting = SocialAuthenticationService.getProviderSetting(scope.provider);
                scope.providerURL = SocialAuthenticationService.getProviderURL(scope.provider);
                scope.csrf = SocialAuthenticationService.getCSRF();
            }
        }
    });

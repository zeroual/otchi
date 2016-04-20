'use strict';
angular.module('socialAuthentication', []);
angular.module('socialAuthentication')
    .config(function ($stateProvider) {
        $stateProvider
            .state('social-register', {
                url: "/social-register/:provider?success&error",
                data: {
                    pageTitle: 'Social Sing up'
                },
                views: {
                    'content@': {
                        templateUrl: app_dir + "social-authentication/views/social-register.html",
                        controller: 'SocialRegisterController'
                    }
                }
            });

    });

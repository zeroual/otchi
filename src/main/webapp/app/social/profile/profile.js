'use strict';
angular.module('stream')
    .config(function ($stateProvider) {
        $stateProvider
            .state('profile', {
                parent: 'main',
                abstract: true,
                resolve: {
                    translatePartialLoader: function ($translatePartialLoader) {
                        $translatePartialLoader.addPart('profile');
                    }
                }
            })
            .state('showProfile', {
                url: "/chef/:chefId",
                parent: 'main',
                data: {
                    pageTitle: 'Chef profile'
                },
                views: {
                    'content@': {
                        template: "<chef-profile/>"
                    }
                }
            });
    });

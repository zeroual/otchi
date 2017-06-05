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
                url: '/chef/:chefId',
                parent: 'main',
                data: {
                    pageTitle: 'Chef profile'
                },
                views: {
                    'content@': {
                        template: '<chef-profile chef="chef"/>',
                        controller: function (chef, $scope) {
                            $scope.chef = chef;
                        },
                        resolve: {
                            chef: function ($stateParams, $resource) {
                                var params = {id: $stateParams.chefId};
                                return $resource('/rest/v1/chef/:id', {id: '@id'}).get(params);
                            }
                        }
                    }
                },
            });
    });

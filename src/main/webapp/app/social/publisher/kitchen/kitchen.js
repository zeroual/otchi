'use strict';
angular.module('kitchen', ['ui.router']);
angular.module('kitchen')
    .config(function ($stateProvider) {
        $stateProvider
            .state('newRecipe', {
                parent: 'main',
                url: "/recipe/new",
                data: {
                    pageTitle: 'Create new recipe'
                },
                views: {
                    'content@': {
                        templateUrl: app_dir + "/social/publisher/kitchen/views/kitchen.html"
                    }
                }
            })
            .state('showRecipe', {
                parent: 'main',
                url: "/recipe/:feedId",
                data: {
                    pageTitle: 'Recipe details'
                },
                views: {
                    'content@': {
                        template: '<recipe-details-viewer feed="feed"/>',
                        controller: function (feed, $scope) {
                            $scope.feed = feed;
                        },
                        resolve: {
                            feed: function ($stateParams, FeedsService) {
                                return FeedsService.fetchFeed($stateParams.feedId).$promise;
                            }
                        }
                    }
                }
            });
    });

'use strict';
angular.module('publisher')
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
                        template: '<recipe-publisher/>'
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
            })
            .state('newStory', {
                parent: 'main',
                url: "/story/new",
                data: {
                    pageTitle: 'Publish new story'
                },
                views: {
                    'content@': {
                        template: '<story-publisher/>'
                    }
                }
            });
    });

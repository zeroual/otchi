'use strict';
angular.module('stream')
    .config(function ($stateProvider) {
        $stateProvider
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
            .state('showFeed', {
                parent: 'main',
                url: "/feed/:feedId",
                data: {
                    pageTitle: 'Feed details'
                },
                views: {
                    'content@': {
                        template: '<feed-details-viewer feed="feed"/>',
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
            .state('showPost', {
                parent: 'main',
                url: "/post/:postId",
                data: {
                    pageTitle: 'Post details'
                },
                views: {
                    'content@': {
                        template: '<feed-viewer feed="feed"/>',
                        controller: function (feed, $scope) {
                            $scope.feed = feed;
                        },
                        resolve: {
                            feed: function ($stateParams, FeedsService) {
                                return FeedsService.fetchFeed($stateParams.postId).$promise;
                            }
                        }
                    }
                }
            });
    });

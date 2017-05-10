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

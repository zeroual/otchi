'use strict';
angular.module('publisher')
    .config(function ($stateProvider) {
        $stateProvider
            .state('publisher', {
                parent: 'main',
                abstract: true,
                resolve: {
                    translatePartialLoader: function ($translatePartialLoader) {
                        $translatePartialLoader.addPart('publisher');
                    }
                }
            })
            .state('createRecipe', {
                parent: 'publisher',
                url: "/recipe/create",
                data: {
                    pageTitle: 'Create new recipe'
                },
                views: {
                    'content@': {
                        template: '<recipe-summary/>'
                    }
                }
            })
            .state('addIngredients', {
                parent: 'publisher',
                url: "/recipe/ingredients",
                data: {
                    pageTitle: 'Create new recipe'
                },
                views: {
                    'content@': {
                        template: '<recipe-ingredients/>'
                    }
                }
            })
            .state('addInstructions', {
                parent: 'publisher',
                url: "/recipe/instructions",
                data: {
                    pageTitle: 'Create new recipe'
                },
                views: {
                    'content@': {
                        template: '<recipe-instructions/>'
                    }
                }
            })
            .state('addImages', {
                parent: 'publisher',
                url: "/recipe/images",
                data: {
                    pageTitle: 'Create new recipe'
                },
                views: {
                    'content@': {
                        template: '<recipe-images/>'
                    }
                }
            })
            .state('newStory', {
                parent: 'publisher',
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

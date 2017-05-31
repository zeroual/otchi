'use strict';
angular.module('publisher')
    .config(function ($stateProvider) {
        $stateProvider
            .state('createRecipe', {
                parent: 'main',
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
                parent: 'main',
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
                parent: 'main',
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
                parent: 'main',
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

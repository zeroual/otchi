'use strict';
angular.module('kitchen', ['ui.router', 'ngFileUpload']);
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
            });

    });

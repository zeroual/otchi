
var app=angular.module("otchi");
var app_dir = "app/";
app.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/feed");

    $stateProvider
        .state('feed', {
            url: "/feed",
            templateUrl: app_dir+"kitchen/views/feed.html",
            controller:'RecipeController'
        })
        .state('kitchen', {
            url: "/kitchen",
            templateUrl: app_dir+"kitchen/views/kitchen.html",
            controller:'RecipeController'
        });
});
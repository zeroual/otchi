
var app=angular.module("otchi");
var app_dir = "app/";
app.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/feed");

    $stateProvider
        .state('feed', {
            url: "/feed",
            templateUrl: app_dir + "social/stream/views/feed.html",
            controller: 'FeedsLoaderController'
        });
});
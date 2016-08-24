var app = angular.module("otchi");
var app_dir = "app/";
app.config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise("/");
    $stateProvider
        .state('index', {
            url: "/",
            data: {
                pageTitle: 'Otchi Beta'
            },
            views: {
                'navbar@': {
                    templateUrl: app_dir + 'components/welcome/header.html',
                },
                'content@': {
                    templateUrl: app_dir + "components/welcome/welcome.html"
                }
            }
        })
        .state('main', {
            'abstract': true,
            views: {
                'navbar@': {
                    templateUrl: app_dir + 'components/navbar/navbar.html',
                    controller: 'NavBarController'
                }
            }
        })

        .state('feed', {
            url: "/feed",
            parent: 'main',
            data: {
                pageTitle: 'Feed'
            },
            views: {
                'content@': {
                    templateUrl: app_dir + "social/stream/views/feed.html",
                    controller: 'FeedsLoaderController'
                },
                'right-rail@feed': {
                    templateUrl: app_dir + 'social/stream/views/followings-recommendation.html',
                    controller: 'FollowingsRecommendationController'
                }
            }
        })
        .state('showPost', {
            parent: 'main',
            url: "/post/:postId",
            data: {
                pageTitle: 'Post details'
            },
        });
});
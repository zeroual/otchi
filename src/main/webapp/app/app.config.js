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
                'content@': {
                    templateUrl: app_dir + "landing/landing.html",
                    controller: 'LandingController'
                }
            },
            resolve: {
                translatePartialLoader: function ($translatePartialLoader) {
                    $translatePartialLoader.addPart('landing');
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
                    template:'<timeline/>'
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

        })
        .state('showProfile', {
            url: "/profile/:profileId",
            parent: 'main',
            data: {
                pageTitle: 'User profile'
            },
            views: {
                'content@': {
                    templateUrl: app_dir + "social/profile/views/profile.html",
                    controller: 'ProfileController'
                }
            }

        });
}).config(function ($translateProvider) {

    $translateProvider.useLoader('$translatePartialLoader', {
        urlTemplate: 'i18n/{lang}/{part}.json'
    });
    $translateProvider.preferredLanguage('fr');
    $translateProvider.useSanitizeValueStrategy('escaped');
    $translateProvider.useCookieStorage();
});

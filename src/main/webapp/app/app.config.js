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
                'main@': {
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
                    template:'<navbar/>'
                }
            },
            resolve: {
                translatePartialLoader: function ($translatePartialLoader) {
                    $translatePartialLoader.addPart('main');
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
}).config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
    cfpLoadingBarProvider.includeSpinner = false;
}]);

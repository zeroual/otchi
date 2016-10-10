angular.module('authentication', ['ngResource', 'LocalStorageModule', 'ui.router', 'socket'])
    .run(function ($rootScope, $location, $window, $http, $state, Auth, Principal) {

        $rootScope.$on('$stateChangeStart', function (event, toState) {
            if (Principal.isIdentityResolved()) {
                Principal.identity()
                    .then(function () {
                        var isAuthenticated = Principal.isAuthenticated();
                        // an authenticated user can't access to login and register pages
                        if (isAuthenticated && (toState.name === 'login' || toState.name === 'register')) {
                            $state.go('feed');
                        }
                    });

            }
        });
    })
    .config(function ($httpProvider, $stateProvider) {
        //enable CSRF
        $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
        $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
        // add interceptor to 401 error
        $httpProvider.interceptors.push('authExpiredInterceptor');

        $stateProvider
            .state('login', {
                url: "/login",
                data: {
                    pageTitle: 'Login'
                },
                views: {
                    'content@': {
                        templateUrl: app_dir + "authentication/views/login.html",
                        controller: 'LoginController'
                    }
                }
            }).state('register', {
            url: "/register",
            data: {
                pageTitle: 'register'
            },
            views: {
                'content@': {
                    templateUrl: app_dir + "authentication/views/register.html",
                    controller: 'RegisterController'
                }
            }
        });
    })
    .constant('LOGIN_URL', '/rest/v1/login')
    .constant('LOGOUT_URL', '/rest/v1/logout')
    .constant('ACCOUNT_URL', '/rest/v1/me')
    .constant('REGISTER_URL', '/rest/v1/account/register');
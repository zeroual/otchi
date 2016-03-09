var app = angular.module("otchi");
app.run(function ($rootScope, $location, $window, $http, $state) {

    var updateTitle = function (titleKey) {
        if (!titleKey && $state.$current.data && $state.$current.data.pageTitle) {
            titleKey = $state.$current.data.pageTitle;
        }
        $window.document.title = titleKey || 'Otchi';
    };

    $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
        $rootScope.toState = toState;
        $rootScope.toStateParams = toStateParams;
    });

    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
        var titleKey = 'Otchi';

        if (toState.name != 'login' && $rootScope.previousStateName) {
            $rootScope.previousStateName = fromState.name;
            $rootScope.previousStateParams = fromParams;
        }

        // Set the page title key to the one configured in state or use default one
        if (toState.data.pageTitle) {
            titleKey = toState.data.pageTitle;
        }
        updateTitle(titleKey);
    });

    $rootScope.back = function () {
        // If previous state is 'activate' or do not exist go to 'home'
        if ($rootScope.previousStateName === 'activate' || $state.get($rootScope.previousStateName) === null) {
            $state.go('index');
        } else {
            $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
        }
    };
});
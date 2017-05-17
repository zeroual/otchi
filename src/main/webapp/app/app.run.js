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

        if (toState.name != 'login') {
            $rootScope.previousStateName = toState;
            $rootScope.previousStateParams = toStateParams;
        }
    });

    $rootScope.$on('$stateChangeSuccess', function (event, toState) {
        var titleKey = 'Otchi';

        // Set the page title key to the one configured in state or use default one
        if (toState.data.pageTitle) {
            titleKey = toState.data.pageTitle;
        }
        updateTitle(titleKey);
    });

    $rootScope.back = function () {
        // If previous state is not exist go to 'index' page
        if ($state.get($rootScope.previousStateName) == undefined) {
            $state.go('index');
        } else {
            $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
        }
    };
}).run(function ($rootScope, $translate) {
    $rootScope.$on('$translatePartialLoaderStructureChanged', function () {
        $translate.refresh();
    });
});
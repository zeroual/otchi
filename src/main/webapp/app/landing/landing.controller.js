angular.module('otchi')
    .controller('LandingController', function ($scope, $translate) {

        $scope.changeLanguage = function (langKey) {
            $translate.use(langKey);
        };
    });

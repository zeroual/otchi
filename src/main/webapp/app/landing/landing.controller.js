angular.module('otchi')
    .controller('LandingController', function ($scope, $translate, $location, $anchorScroll) {

        $scope.changeLanguage = function (langKey) {
            $translate.use(langKey);
        };

        $scope.scrollTo = function (id) {
            $location.hash(id);
            $anchorScroll();
        };

    });

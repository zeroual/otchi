angular.module('otchi')
    .controller('LandingController', function ($scope, $translate) {

        $scope.changeLanguage = function (langKey) {
            var doc = document.getElementById("otchi-page-container");
            if (langKey == 'ar') {
                doc.style.direction = "rtl";
            } else {
                doc.style.direction = "ltr";
            }
            $translate.use(langKey);
            track("change-language", {
                language: $scope.usedLanguage()
            });
        };

        $scope.usedLanguage = function () {
            return $translate.use();
        };
    });

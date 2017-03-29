angular.module('otchi')
    .controller('LandingController', function ($scope, $translate) {

        function getLanguageLabel(langKey) {
            return languagesLabels[langKey]
        }

        var languagesLabels = {
            'fr': 'Français',
            'ar': 'العربية',
            'en': 'English'
        };

        $scope.usedLanguage = getLanguageLabel($translate.use());

        $scope.changeLanguage = function (langKey) {
            $translate.use(langKey);
            $scope.usedLanguage = getLanguageLabel(langKey);
        };


    });

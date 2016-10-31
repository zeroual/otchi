angular.module('profile')
    .directive('profile', function ($rootScope, ShareService) {
        return {
            restrict: 'E',
            templateUrl: 'app/social/profile/directives/profile-show-template.html',
            controller: function ($scope) {

                function init() {
                    $scope.story = {
                        content: '',
                        images: []
                    };
                }

                init();

            }
        };
    });

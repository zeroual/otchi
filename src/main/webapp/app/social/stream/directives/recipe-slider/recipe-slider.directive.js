angular.module("stream")
    .directive('recipeSlider', function () {
        return {
            scope: {
                images: "="
            },
            templateUrl: 'app/social/stream/directives/recipe-slider/recipe-slider.template.html',
            controller: function ($scope) {
                if($scope.images.length == 0){
                    $scope.images.push('/assets/images/icons/no_image_available.svg');
                }
            }
        };
    });
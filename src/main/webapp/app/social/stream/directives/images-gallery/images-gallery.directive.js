angular.module("stream")
    .directive('imagesGallery', function () {
        return {
            scope: {
                images: "="
            },
            templateUrl: 'app/social/stream/directives/images-gallery/images-gallery.template.html',
            controller: function ($scope) {
                $scope.displayTowImages = function (index) {
                    return !(index % 2 == 0 && !$scope.images[index + 1]);
                }
            }
        };
    });

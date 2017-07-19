angular.module("stream")
    .component('imagesCarousel', {
        bindings: {
            images: "<"
        },
        templateUrl: 'app/social/stream/directives/images-carousel/images-carousel.html',
        controller: function () {
            var ctrl = this;
            ctrl.interval = 3000;
            ctrl.slides = [];

            ctrl.$onInit = function () {
                ctrl.slides = ctrl.images.map(function (image, index) {
                    return {
                        image: image,
                        id: index
                    }
                });
            };

        }
    });

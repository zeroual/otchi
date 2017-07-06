angular.module("publisher")
    .component('recipeImages', {
        templateUrl: 'app/social/publisher/components/recipe-wizard/recipe-images/recipe-images.html',
        controller: function (localStorageService, ShareService, ToasterService, ImageBase64Encoder, $state) {

            var ctrl = this;
            ctrl.$onInit = function () {
                var recipe = localStorageService.get('recipe');

                if (recipe != undefined) {
                    ctrl.recipe = recipe;
                } else {
                    ctrl.recipe = {};
                }

                ctrl.images = [];
                var imagesBase64 = localStorageService.get('recipe-images');
                if (imagesBase64 != undefined) {
                    imagesBase64.forEach(function (imageBase64) {
                        var image = ImageBase64Encoder.decode(imageBase64);
                        ctrl.images.push(image);
                    });
                }

            };

            ctrl.previousStep = function () {
                $state.go('addInstructions');
            };

            ctrl.shareRecipe = function () {
                ctrl.recipe.pictures = ctrl.images;
                ShareService.publishRecipe(ctrl.recipe).then(function (feed) {
                    localStorageService.set('recipe', {});
                    ctrl.images = [];
                    $state.go('showFeed', {feedId: feed.id});
                }).catch(function () {
                    ToasterService.error('post.recipe.failed');
                });
            };

            ctrl.deleteImage = function (image) {
                var index = ctrl.images.indexOf(image);
                ctrl.images.splice(index, 1);
            };

            ctrl.$onDestroy = function () {
                localStorageService.set('recipe-images', []);
                ImageBase64Encoder.encode(ctrl.images).then(function (imagesBase64) {
                    localStorageService.set('recipe-images', imagesBase64);
                });
            };
        }
    });

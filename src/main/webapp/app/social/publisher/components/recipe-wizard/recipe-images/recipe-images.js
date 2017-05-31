angular.module("publisher")
    .component('recipeImages', {
        templateUrl: 'app/social/publisher/components/recipe-wizard/recipe-images/recipe-images.html',
        controller: function (localStorageService, ShareService, ToasterService, Upload, $state) {

            var ctrl = this;
            ctrl.$onInit = function () {
                var recipe = localStorageService.get('recipe');

                if (recipe != undefined) {
                    ctrl.recipe = recipe;
                } else {
                    ctrl.recipe = {};
                }

                ctrl.images = [];
                var dataUrls = localStorageService.get('recipe-images');
                if (dataUrls != undefined) {
                    dataUrls.forEach(function (dataurl) {
                        var blob = Upload.dataUrltoBlob(dataurl, name);
                        ctrl.images.push(blob);
                    });
                }

            };

            ctrl.saveImages = function () {
                Upload.base64DataUrl(ctrl.images).then(function (b64) {
                    localStorageService.set('recipe-images', b64);
                });
            };


            ctrl.previousStep = function () {
                ctrl.saveImages();
                $state.go('addInstructions');
            };

            ctrl.shareRecipe = function () {
                ctrl.saveImages();
                ctrl.recipe.pictures = ctrl.images;
                ShareService.publishRecipe(ctrl.recipe).then(function (feed) {
                    localStorageService.set('recipe', {});
                    localStorageService.set('recipe-images', []);
                    console.log("recipe has been published");
                    $state.go('showFeed', {feedId: feed.id});
                }).catch(function () {
                    ToasterService.error('post.recipe.failed');
                });
            };

            ctrl.deleteImage = function (image) {
                var index = ctrl.images.indexOf(image);
                ctrl.images.splice(index, 1);
            };
        }
    });

angular.module("publisher")
    .component('recipeSummary', {
        templateUrl: 'app/social/publisher/components/recipe-wizard/recipe-summary/recipe-summary.html',
        controller: function (localStorageService,$state) {

            var ctrl = this;
            ctrl.$onInit = function () {
                ctrl.recipe = localStorageService.get('recipe');
                if (ctrl.recipe == undefined) {
                    ctrl.recipe = {};
                }
            };

            ctrl.nextStep = function () {
                localStorageService.set('recipe', ctrl.recipe);
                $state.go('addIngredients');
            }
        }
    });

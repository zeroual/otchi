angular.module("publisher")
    .component('recipeInstructions', {
        templateUrl: 'app/social/publisher/components/recipe-wizard/recipe-instructions/recipe-instructions.html',
        controller: function (localStorageService, $state) {

            var ctrl = this;
            var recipe;

            ctrl.$onInit = function () {
                ctrl.instructions = [{}, {}, {}];
                recipe = localStorageService.get('recipe');
                if (recipe == undefined) {
                    recipe = {}
                } else if (recipe.instructions) {
                    ctrl.instructions = recipe.instructions;
                }
            };

            ctrl.previousStep = function () {
                $state.go('addIngredients');
            };

            ctrl.nextStep = function () {
                $state.go('addImages');
            };


            ctrl.addInstruction = function () {
                ctrl.instructions.push({})
            };

            ctrl.removeInstruction = function (index) {
                ctrl.instructions.splice(index, 1);
            };

            ctrl.$onDestroy = function () {
                recipe.instructions = ctrl.instructions.filter(function (instruction) {
                    return instruction.content != undefined && instruction.content != '';
                });
                localStorageService.set('recipe', recipe);
            };
        }
    });

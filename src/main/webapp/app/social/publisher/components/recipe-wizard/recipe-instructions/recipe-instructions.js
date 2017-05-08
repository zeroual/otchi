angular.module("publisher")
    .component('recipeInstructions', {
        templateUrl: 'app/social/publisher/components/recipe-wizard/recipe-instructions/recipe-instructions.html',
        controller: function (localStorageService, $state) {

            var ctrl = this;
            var recipe;

            ctrl.$onInit = function () {
                ctrl.instructions = [{}];

                recipe = localStorageService.get('recipe');
                if (recipe == undefined) {
                    recipe = {}
                } else if (recipe.instructions) {
                    ctrl.instructions = recipe.instructions;
                }
            };

            ctrl.nextStep = function () {
                ctrl.saveRecipeInstructions();
                $state.go('addIngredients');
            };

            ctrl.nextStep = function () {
                ctrl.saveRecipeInstructions();
                $state.go('addImages');
            };


            ctrl.saveRecipeInstructions = function () {
                recipe.instructions = ctrl.instructions;
                localStorageService.set('recipe', recipe);
            };

            ctrl.addInstruction = function () {
                ctrl.instructions.push({})
            };

            ctrl.removeInstruction = function (index) {
                ctrl.instructions.splice(index, 1);
            };

            ctrl.addImagesStep = function () {
                ctrl.saveInstructions();
            };
        }
    });

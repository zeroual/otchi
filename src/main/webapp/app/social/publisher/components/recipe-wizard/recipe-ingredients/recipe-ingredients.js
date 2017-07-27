angular.module("publisher")
    .component('recipeIngredients', {
        templateUrl: 'app/social/publisher/components/recipe-wizard/recipe-ingredients/recipe-ingredients.html',
        controller: function ($scope, $http, localStorageService, $state) {
            var ctrl = this;
            var recipe;

            ctrl.$onInit = function () {
                ctrl.ingredients = [];
                recipe = localStorageService.get('recipe');
                if (recipe == undefined) {
                    recipe = {};
                } else if (recipe.ingredients != undefined) {
                    ctrl.ingredients = recipe.ingredients;
                }
            };


            ctrl.nextStep = function () {
                ctrl.saveRecipeIngredients();
                $state.go('addInstructions');
            };

            ctrl.previousStep = function () {
                ctrl.saveRecipeIngredients();
                $state.go('createRecipe');
            };
            ctrl.saveRecipeIngredients = function () {
                recipe.ingredients = ctrl.ingredients;
                localStorageService.set('recipe', recipe);
            };

            ctrl.removeIngredient = function (index) {
                ctrl.ingredients.splice(index, 1);
            };

            ctrl.addIngredient = function () {
                var ingredient = {
                    name: ctrl.name,
                    quantity: 1,
                    unit: 'units'
                };
                ctrl.ingredients.push(ingredient);
                ctrl.name = '';
            };
        }
    });

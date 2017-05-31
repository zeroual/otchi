angular.module("publisher")
    .component('recipeIngredients', {
        templateUrl: 'app/social/publisher/components/recipe-wizard/recipe-ingredients/recipe-ingredients.html',
        controller: function ($scope, $http, localStorageService, $state) {
            var ctrl = this;
            var recipe;

            ctrl.$onInit = function () {
                ctrl.ingredients = [{}];
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

            ctrl.addIngredient = function () {
                ctrl.ingredients.push({});
            };

            ctrl.removeIngredient = function (index) {
                ctrl.ingredients.splice(index, 1);
            };

            loadIngredients = function () {
                $http.get('/data/ingredients_dictionary.json').then(function (res) {
                    ctrl.ingredientsDictionary = res.data.map(function (obj) {
                        return obj.name;
                    });
                });
            };

            ctrl.ingredientsDictionary = loadIngredients();
            extractTags = function () {
                return ctrl.tags.map(function (tag) {
                    return tag.text;
                });
            };


            ctrl.addInstructionsStep = function () {
                ctrl.saveIngredients();
            };
        }
    });

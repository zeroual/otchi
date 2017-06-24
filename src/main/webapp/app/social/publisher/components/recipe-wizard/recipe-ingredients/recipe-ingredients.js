angular.module("publisher")
    .component('recipeIngredients', {
        templateUrl: 'app/social/publisher/components/recipe-wizard/recipe-ingredients/recipe-ingredients.html',
        controller: function ($scope, $http, localStorageService, $state, $uibModal) {
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
                $uibModal.open({
                    animation: true,
                    ariaLabelledBy: 'modal-title',
                    ariaDescribedBy: 'modal-body',
                    templateUrl: 'app/social/publisher/components/recipe-wizard/recipe-ingredients/ingredient-quantity.html',
                    size: 'md',
                    controller: function ($scope, $uibModalInstance) {
                        $scope.ingredient = ctrl.name;
                        $scope.ok = function () {
                            var ingredient = {
                                name: ctrl.name,
                                quantity: $scope.quantity,
                                unit: $scope.unit
                            };
                            ctrl.ingredients.push(ingredient);
                            ctrl.name = '';
                            $uibModalInstance.dismiss('ok');
                        };

                        $scope.cancel = function () {
                            $uibModalInstance.dismiss('cancel');
                        }
                    }
                });
            };

            loadIngredients = function () {
                $http.get('/data/ingredients_dictionary.json').then(function (res) {
                    ctrl.ingredientsDictionary = res.data.map(function (obj) {
                        return obj.name;
                    });
                });
            };

            ctrl.ingredientsDictionary = loadIngredients();
        }
    });

angular.module("otchi")
    .component('todayRecipe', {
        templateUrl: 'app/landing/today-recipe/today-recipe.html',
        controller: function ($http) {
            var ctrl = this;
            ctrl.$onInit = function () {
                $http.get('/api/v1/today-recipe').then(function (response) {
                    return response.data;
                }).then(function (bestRecipe) {
                    ctrl.recipe = bestRecipe;
                });
            };
        }
    });
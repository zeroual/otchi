angular.module("publisher")
    .service("PostService", function ($resource) {

        this.publishRecipe = function (recipe) {
            var service = $resource('/rest/v1/post/recipe');
            return service.save(recipe);
        };
    });
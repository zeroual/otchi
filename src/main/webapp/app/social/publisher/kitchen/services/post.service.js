angular.module("publisher")
    .service("PostService", function (Upload) {

        this.publishRecipe = function (recipe) {
            return Upload.upload({
                url: '/rest/v1/post/recipe',
                data: {pictures: recipe.pictures, 'recipe': Upload.jsonBlob(recipe)},
                arrayKey: ''
            })
        };
    });
angular.module("publisher")
    .service("ShareService", function (Upload, $http) {

        this.publishRecipe = function (recipe) {
            return Upload.upload({
                url: '/rest/v1/post/recipe',
                data: {pictures: recipe.pictures, 'recipe': Upload.jsonBlob(recipe)},
                arrayKey: ''
            })
        };

        this.publisherStory = function (story) {
            return $http.post('/rest/v1/post/story', story).then(function (response) {
                return response.data;
            });
        };
    });
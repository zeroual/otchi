angular.module("publisher")
    .service("ShareService", function (Upload) {

        this.publishRecipe = function (recipe) {
            return Upload.upload({
                url: '/rest/v1/post/recipe',
                data: {pictures: recipe.pictures, 'recipe': Upload.jsonBlob(recipe)},
                arrayKey: ''
            }).then(function (response) {
                return response.data;
            });
        };

        this.publisherStory = function (story) {
            return Upload.upload({
                url: '/rest/v1/post/story',
                data: {images: story.images, 'story': Upload.jsonBlob(story)},
                arrayKey: ''
            }).then(function (response) {
                return response.data;
            });
            ;
        };
    });
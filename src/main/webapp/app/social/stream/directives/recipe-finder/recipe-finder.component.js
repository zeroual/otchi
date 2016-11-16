angular.module("stream")
    .component('recipeFinder', {
        bindings: {
            query: '<'
        },
        controller: function (SearchService) {
            var self = this;
            SearchService.searchRecipes(this.query).then(function (recipeSearchResults) {
                self.recipeSearchResults = recipeSearchResults;
            });
        },
        templateUrl: 'app/social/stream/directives/recipe-finder/recipe-finder.template.html'
    });
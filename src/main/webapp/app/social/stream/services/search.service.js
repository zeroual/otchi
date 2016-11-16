angular.module("stream")
    .service("SearchService", function ($http) {

        this.suggestRecipes = function (query) {
            var config = {
                params: {query: query}
            };
            return $http.get('/rest/v1/recipe/search/suggest', config).then(function (response) {
                return response.data;
            });
        };

        this.searchRecipes = function (query) {
            var config = {
                params: {query: query}
            };
            return $http.get('/rest/v1/recipe/search', config).then(function (response) {
                return response.data;
            });
        };
    });
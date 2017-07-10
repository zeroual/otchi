angular.module('profile')
    .service('ChefService', function($resource){

        var ChefFeeds = $resource('/rest/v1/chef/:id/feeds', {id: '@id'});
        this.fetchChefFeeds = function (chefId) {
            return ChefFeeds.query({ id : chefId });
        };

    });
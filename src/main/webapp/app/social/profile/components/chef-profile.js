angular.module("profile")
    .component('chefProfile', {
        bindings: {
            chef: '<'
        },
        templateUrl: 'app/social/profile/components/chef-profile.html',
        controller: function (ChefService) {
            this.feeds = ChefService.fetchChefFeeds(this.chef.id);
        }
    });
angular.module("profile")
    .component('chefProfile', {
        bindings: {
            chef: '<'
        },
        templateUrl: 'app/social/profile/components/chef-profile.html',
        controller: function (ChefService, AnalyticsService) {
            var ctrl = this;
            ctrl.feeds = ChefService.fetchChefFeeds(this.chef.id);

            AnalyticsService.incremenetChefViews(this.chef.id).then(function () {
                ctrl.chef.views++;
            });
        }
    });
angular.module("profile")
    .component('chefProfile', {
        bindings: {
            chef: '<'
        },
        templateUrl: 'app/social/profile/components/chef-profile.html'
    });
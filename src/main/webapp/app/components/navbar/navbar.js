angular.module('otchi').component('navbar', {

    templateUrl: 'app/components/navbar/navbar.html',
    controller: function (Principal) {
        var ctrl = this;

        Principal.identity().then(function () {
            ctrl.isAuthenticated = Principal.isAuthenticated();
        }).catch(function () {
            ctrl.isAuthenticated = false;
        });
    }
});
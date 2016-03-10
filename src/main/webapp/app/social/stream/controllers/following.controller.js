angular.module("stream")
    .controller("FollowingController", function ($scope, $rootScope, FollowingService) {
        $scope.followingUsers = FollowingService.fetchAllfollowers();


        $scope.deletFollowingUser = function (user) {
            var index = $scope.followingUsers.indexOf(user);
            $scope.followingUsers.splice(index, 1);
        }


        $scope.followUser = function (user) {
            FollowingService.follow(user);
            $scope.deletFollowingUser(user);
        }

    });
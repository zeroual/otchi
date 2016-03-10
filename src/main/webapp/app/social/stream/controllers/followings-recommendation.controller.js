angular.module("stream")
    .controller("FollowingsRecommendationController", function ($scope, $rootScope, FollowingService, FollowingsRecommendationService) {

        $scope.followingRecommendations = FollowingsRecommendationService.fetchAll();

        function _removeUserFromFollowingRecommendations(user) {
            var index = $scope.followingRecommendations.indexOf(user);
            $scope.followingRecommendations.splice(index, 1);
        };

        $scope.followUser = function (user) {
            FollowingService.follow(user).then(function () {
                _removeUserFromFollowingRecommendations(user);
            });
        }

    });
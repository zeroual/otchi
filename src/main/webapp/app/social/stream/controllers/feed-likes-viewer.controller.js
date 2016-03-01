angular.module('stream')
    .controller('FeedLikesViewerController', function ($scope, $uibModalInstance, likes) {

        $scope.likes = likes;

        $scope.close = function () {
            $uibModalInstance.dismiss('cancel');
        };
    });
angular.module("publisher")
    .directive('publisher', function () {
        return {
            templateUrl: 'app/social/publisher/views/publisher-directive.html',
            controller: function ($scope) {
                $scope.selectedPublisher = 'STATUS';

                $scope.share = function () {
                    $scope.$broadcast('SHARE' + '_' + $scope.selectedPublisher + '_EVENT');
                };

                $scope.changePublisher = function (publisherToSelect) {
                    $scope.selectedPublisher = publisherToSelect;
                };
            },
        };
    });
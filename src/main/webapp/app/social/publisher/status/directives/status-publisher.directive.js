angular.module("publisher")
    .directive('statusPublisher', function () {
        return {

            templateUrl: 'app/social/publisher/views/status-publisher-directive.html',
            controller: function ($scope) {

                $scope.shareStatus = function () {
                    alert('hello status')
                };
            }
        };
    });
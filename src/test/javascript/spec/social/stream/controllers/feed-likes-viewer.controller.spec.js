describe('FeedLoaderController', function () {

    beforeEach(module('stream', 'fakeModal'));

    var $scope;
    var likes = [{id: 1}, {id: 2}];
    beforeEach(inject(function ($controller, $rootScope, $uibModalInstance) {
        $scope = $rootScope.$new();
        $controller('FeedLikesViewerController', {$scope: $scope, $uibModalInstance: $uibModalInstance, likes: likes});
    }));

    it('should resolve likes form dependency', function () {
        expect($scope.likes).toEqualData(likes);
    });

    describe('close modal', function () {

        var $uibModalInstance;
        beforeEach(inject(function (_$uibModalInstance_) {
            $uibModalInstance = _$uibModalInstance_;
        }));

        it('should close modal', function () {
            spyOn($uibModalInstance, 'dismiss')
            $scope.close();
            expect($uibModalInstance.dismiss).toHaveBeenCalledWith('cancel');
        });
    });


});
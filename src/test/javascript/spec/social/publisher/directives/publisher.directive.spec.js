describe('Publisher directive', function () {

    var $scope;
    var $httpBackend;
    beforeEach(module('publisher'));
    beforeEach(module('directives.templates'));

    beforeEach(inject(function ($rootScope, $compile, _$httpBackend_) {
        $httpBackend = _$httpBackend_;
        $scope = $rootScope.$new();
        var element = angular.element('<publisher/>');
        $compile(element)($scope);
        $scope.$digest();
        $scope = element.isolateScope() || element.scope()
    }));


    describe('Switcher', function () {
        it('should select the status publisher by default', function () {
            expect($scope.selectedPublisher).toEqual('STATUS');
        });

        it('should allow to switch the publisher when we change the tab', function () {
            var anotherPublisher = "RECIPE";
            $scope.changePublisher(anotherPublisher);
            expect($scope.selectedPublisher).toEqual(anotherPublisher);
        });
    });
    describe("On share ", function () {
        beforeEach(function () {
            spyOn($scope, '$broadcast');
        });

        it("should broadcast an event to delegate sharing post to selected publisher", function () {
            $scope.changePublisher("RECIPE");
            $scope.share();
            expect($scope.$broadcast).toHaveBeenCalledWith('SHARE_RECIPE_EVENT');
        });
    });


});
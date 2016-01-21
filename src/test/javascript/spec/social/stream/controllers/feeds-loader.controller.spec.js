describe('FeedLoaderController', function () {

    beforeEach(module('stream'));

    var $scope;
    var $httpBackend;
    var posts = [{data: 'foo'}, {data: 'bar'}];

    beforeEach(inject(function ($controller, $rootScope, _$httpBackend_) {
        $scope = $rootScope.$new();
        $httpBackend = _$httpBackend_;
        $httpBackend.expectGET('/rest/v1/feed').respond(posts);
        $controller('FeedsLoaderController', {$scope: $scope});
        $httpBackend.flush();
    }));

    describe('on load', function () {
        it('should load all feeds from the server to show them', function () {
            expect($scope.feeds).toEqualData(posts);
        });
    });

});
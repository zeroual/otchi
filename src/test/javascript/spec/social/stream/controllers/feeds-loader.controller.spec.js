describe('FeedLoaderController', function () {

    beforeEach(module('stream'));

    var $scope;
    var $rootScope;
    var $httpBackend;
    var posts = [{data: 'foo'}, {data: 'bar'}];

    beforeEach(inject(function ($controller, _$rootScope_, _$httpBackend_) {
        $rootScope = _$rootScope_;
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

        it('should listen if new post is published', function () {
            spyOn($scope, 'loadNewPublishedPost');
            var newFeed = {id: 1, data: 'newFeed'};
            $rootScope.$broadcast('NEW_POST_PUBLISHED_EVENT', newFeed);
            expect($scope.loadNewPublishedPost).toHaveBeenCalledWith(newFeed);
        });

        describe('add new post to feed loader', function () {
            it('should add the new feed in the top', function () {
                var newFeed = {id: 1, data: 'newFeed'};
                $scope.loadNewPublishedPost(newFeed);
                expect($scope.feeds[0]).toEqual(newFeed);
            });
        });
    });

});
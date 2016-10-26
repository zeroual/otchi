describe('FeedLoaderController', function () {

    beforeEach(module('stream'));

    var $scope;
    var $rootScope;
    var posts = [{data: 'foo'}, {data: 'bar'}];
    var FeedsService;
    var $httpBackend;
    beforeEach(inject(function ($controller, _$rootScope_, _$httpBackend_, _FeedsService_, Principal) {
        FeedsService = _FeedsService_;
        $rootScope = _$rootScope_;
        $httpBackend = _$httpBackend_;
        $scope = $rootScope.$new();
        spyOn(Principal, 'identity').and.callFake(function () {
            return {
                then: function (cb) {
                    return cb({id: 2});
                }
            };
        });
        spyOn(FeedsService, 'fetchAllFeeds').and.returnValue(posts);
        $controller('FeedsLoaderController', {$scope: $scope, FeedsService: FeedsService});
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
                expect($scope.feeds[0]).toEqualData(newFeed);
                expect($scope.feeds[0].likes).toEqualData([]);
                expect($scope.feeds[0].liked).toBeFalsy();
            });
        });
    });

    describe('remove Feed', function () {

        it('should listen if post is deleted', function () {
            spyOn($scope, 'deletePost');
            var feedToDelete = {id: 1};
            $rootScope.$broadcast('REMOVE_POST_PUBLISHED_EVENT', feedToDelete);
            expect($scope.deletePost).toHaveBeenCalledWith(feedToDelete);
        });

        it('should remove the feed from feeds', function () {
            var feedToDelete = {id: 1};
            $scope.deletePost(feedToDelete);
            var nbFeedsAfterDelete = $scope.feeds.length;
            expect(nbFeedsAfterDelete).toBe(2);
            expect($scope.feeds.map(function(post) {
                return post.id;
            }).indexOf(feedToDelete.id)).toBe(-1);
        });

    });
});
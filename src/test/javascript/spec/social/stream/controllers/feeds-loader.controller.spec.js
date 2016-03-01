describe('FeedLoaderController', function () {

    beforeEach(module('stream'));

    var $scope;
    var $rootScope;
    var posts = [{data: 'foo'}, {data: 'bar'}];
    var FeedsService;
    beforeEach(inject(function ($controller, _$rootScope_, _$httpBackend_, _FeedsService_, Principal) {
        FeedsService = _FeedsService_;
        $rootScope = _$rootScope_;
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

    describe('like post', function () {
        var post = {id: 1, likes: []};
        it('should the current user int the likes user list', function () {
            $scope.likePost(post);
            expect(post.likes.length).toEqual(1);
        });

        it('should marque that post is liked', function () {
            $scope.likePost(post);
            expect(post.liked).toBeTruthy();
        });
    });


    describe('unlike post', function () {
        var post = {id: 1, likes: [{id: 2}]};
        it('should remove the current user from likes list', function () {
            $scope.unLikePost(post);
            expect(post.likes.length).toEqual(0);
        });
    });

    describe('show the likes list modal', function () {

        var $uibModal;
        beforeEach(inject(function (_$uibModal_) {
            $uibModal = _$uibModal_;
        }));

        it('should open the modal to show likes list', function () {
            spyOn($uibModal, 'open');
            var feed = {likes: []};
            $scope.showLikes(feed);
            expect($uibModal.open).toHaveBeenCalled();
        });
    });


});
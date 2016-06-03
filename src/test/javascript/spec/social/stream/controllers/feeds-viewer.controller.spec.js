describe('FeedViewer Directive', function () {

    beforeEach(module('stream'));
    beforeEach(module('directives.templates'));

    var $scope;
    var $httpBackend;
    beforeEach(inject(function ($rootScope, _$httpBackend_, FeedsService, Principal, $compile) {
        $httpBackend = _$httpBackend_;
        spyOn(Principal, 'identity').and.callFake(function () {
            return {
                then: function (cb) {
                    return cb({id: 2});
                }
            };
        });

        var posts = [{data: 'foo'}, {data: 'bar'}];
        spyOn(FeedsService, 'fetchAllFeeds').and.returnValue(posts);
        $scope = $rootScope.$new();
        var element = angular.element('<feed-viewer/>');
        $compile(element)($scope);
        $scope.$digest();
        $scope = element.isolateScope() || element.scope();
    }));

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

    describe('comment on post', function () {

        var post;
        beforeEach(function () {
            post = {id: 1, comments: []};
            $scope.feed = {id: 1, comments: []};
            $httpBackend.expectPOST('/rest/v1/feed/1/comment', 'comment content').respond(200, {id: 2, data: 'foo'});
        });

        it('should ask the server to add save comment', function () {
            $scope.commentContent = "comment content";
            $scope.commentOnPost(post);
            $httpBackend.flush();
        });

        it('should add the new comment to comments list in feed', function () {
            $scope.commentContent = "comment content";
            $scope.commentOnPost(post);
            $httpBackend.flush();
            expect(post.comments).toEqualData([{id: 2, data: 'foo'}]);
        });

        it('should reset the comment content input after commenting', function () {
            $scope.commentContent = "comment content";
            $scope.commentOnPost(post);
            $httpBackend.flush();
            expect($scope.commentContent).toEqual('');
        });
    });

});
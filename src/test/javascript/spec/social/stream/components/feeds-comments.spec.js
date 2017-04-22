describe('Feed comments viewer Directive', function () {

    beforeEach(module('stream'));
    beforeEach(module('directives.templates'));

    var $scope;
    var $httpBackend;
    beforeEach(inject(function ($rootScope, _$httpBackend_, FeedsService, Principal, $compile) {
        $httpBackend = _$httpBackend_;
        $scope = $rootScope.$new();
        var element = angular.element('<feed-comments/>');
        $compile(element)($scope);
        $scope.$digest();
    }));

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
describe('Recipe publisher directive', function () {

    var $scope;
    var $httpBackend;
    beforeEach(module('publisher'));

    beforeEach(module('directives.templates'));

    var $rootScope;

    beforeEach(inject(function (_$rootScope_, $compile, _$httpBackend_) {
        $httpBackend = _$httpBackend_;
        $rootScope = _$rootScope_;
        $scope = $rootScope.$new();
        var element = angular.element('<story-publisher/>');
        $compile(element)($scope);
        $scope.$digest();
    }));


    it('should init story with empty', function () {
        expect($scope.story.content).toBe('');
    });

    describe('when we user click in share', function () {
        var createdPost = {data: 'data'};

        beforeEach(function () {
            $scope.story = {
                content: 'tonight i will eat in this restaurant, any recommendations plz'
            };
            $httpBackend.expectPOST('/rest/v1/post/story', $scope.story).respond(201, createdPost);
        });

        it('should ask the server to save the new story published', function () {
            $scope.shareStory();
            $httpBackend.flush();
            $httpBackend.verifyNoOutstandingExpectation();
        });

        it('should init the story form', function () {
            $scope.shareStory();
            $httpBackend.flush();
            expect($scope.story.content).toEqual('');
        });

        it('should notify the feed loader that a new story is published', function () {
            spyOn($rootScope, '$broadcast').and.returnValue({preventDefault: true});
            $scope.shareStory();
            $httpBackend.flush();
            jasmine.addCustomEqualityTester(angular.equals);
            expect($scope.$broadcast).toHaveBeenCalledWith("NEW_POST_PUBLISHED_EVENT", createdPost);
        });
    });

});

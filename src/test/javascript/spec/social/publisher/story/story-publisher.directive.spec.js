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
                content: 'tonight i will eat in this restaurant, any recommendations plz',
                images: [{name: "image1"}, {name: "image2"}]
            };
            //FIXME (abdellah) i can't test the request payload sent to serer now
            //i hope that i will find  a solution for that :( 
            $httpBackend.expectPOST('/rest/v1/post/story').respond(201, createdPost);
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
            expect($scope.story.images).toEqual([]);
        });

        it('should notify the feed loader that a new story is published', function () {
            spyOn($rootScope, '$broadcast').and.returnValue({preventDefault: false});
            $scope.shareStory();
            $httpBackend.flush();
            $rootScope.$apply();
            jasmine.addCustomEqualityTester(angular.equals);
            expect($scope.$broadcast).toHaveBeenCalledWith("NEW_POST_PUBLISHED_EVENT", createdPost);
        });
    });

    describe('images uploader', function () {

        it('should have empty image on load', function () {
            expect($scope.story.images).toEqual([]);
        });

        it('should remove selected image', function () {
            var image1 = {name: "image1"};
            var image2 = {name: "image2"};
            $scope.story.images = [image1, image2];
            $scope.deleteImage(image1);
            expect($scope.story.images).toEqual([image2]);
        });
    });

});

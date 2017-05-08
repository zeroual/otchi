describe('Recipe publisher directive', function () {

    var ctrl;
    var $httpBackend;
    var $state;

    beforeEach(module('otchi'));
    beforeEach(mockI18nCalls());

    beforeEach(module('directives.templates'));

    beforeEach(inject(function (_$httpBackend_, $componentController, _$state_) {
        $httpBackend = _$httpBackend_;
        $state = _$state_;
        ctrl = $componentController('storyPublisher', null, {});
    }));


    it('should init story with empty', function () {
        expect(ctrl.story.content).toBe('');
    });

    describe('when we user click in share', function () {
        var createdPost = {data: 'data'};

        beforeEach(function () {
            ctrl.story = {
                content: 'tonight i will eat in this restaurant, any recommendations plz',
                images: [{name: "image1"}, {name: "image2"}]
            };
            //FIXME (abdellah) i can't test the request payload sent to serer now
            //i hope that i will find  a solution for that :( 
            $httpBackend.expectPOST('/rest/v1/post/story').respond(201, createdPost);
        });

        it('should ask the server to save the new story published', function () {
            ctrl.shareStory();
            $httpBackend.flush();
            $httpBackend.verifyNoOutstandingExpectation();
        });

        it('should redirect to feed page after success publish', function () {
            spyOn($state, 'go');
            ctrl.shareStory();
            $httpBackend.flush();
            expect($state.go).toHaveBeenCalledWith('feed');
        });
    });

    describe('images uploader', function () {

        it('should have empty image on load', function () {
            expect(ctrl.story.images).toEqual([]);
        });

        it('should remove selected image', function () {
            var image1 = {name: "image1"};
            var image2 = {name: "image2"};
            ctrl.story.images = [image1, image2];
            ctrl.deleteImage(image1);
            expect(ctrl.story.images).toEqual([image2]);
        });
    });

});

describe('Recipe publisher directive', function () {

    var ctrl;
    var $httpBackend;
    var $state;
    var ImageBase64Encoder, localStorageService;
    beforeEach(module('otchi'));
    beforeEach(module('directives.templates'));
    beforeEach(mockI18nCalls);

    beforeEach(inject(function (_$httpBackend_, $componentController, _$state_, _ImageBase64Encoder_, _localStorageService_) {
        $httpBackend = _$httpBackend_;
        $state = _$state_;
        ctrl = $componentController('storyPublisher', null, {});
        ImageBase64Encoder = _ImageBase64Encoder_;
        localStorageService = _localStorageService_;
        ctrl.$onInit();
    }));


    describe("on load", function () {
        it('should init story with empty if not story found in the local storage', function () {
            spyOn(localStorageService, 'get').and.returnValue(undefined);
            ctrl.$onInit();
            expect(ctrl.story.content).toBe('');
        });

        it('should init story with the found in the local storage', function () {
            var story = {data: 'some date'};
            spyOn(localStorageService, 'get').and.callFake(function (key) {
                if (key == 'story') {
                    return story
                }
            });
            ctrl.$onInit();
            expect(ctrl.story).toBe(story);
        });

        it('should have empty image on load', function () {
            spyOn(localStorageService, 'get').and.returnValue(undefined);
            expect(ctrl.images).toEqual([]);
        });

        it('should init story with the found in the local storage', function () {
            var blob = new Blob();
            spyOn(ImageBase64Encoder, 'decode').and.returnValue(blob);
            var imagesBase64 = ["data:image/jpeg;base64,/9j/4AA", "data:image/jpeg;base64,/9j/4U22"];
            spyOn(localStorageService, 'get').and.callFake(function (key) {
                if (key == 'story-images') {
                    return imagesBase64;
                }
            });
            ctrl.$onInit();
            expect(ctrl.images).toEqualData([blob, blob]);
        });

    });

    describe('on destroy', function () {

        var $q, $rootScope;
        beforeEach(inject(function (_$q_, _$rootScope_) {
            $q = _$q_;
            $rootScope = _$rootScope_;
        }));
        it('should save the story in the local storage', function () {
            ctrl.story = {data: "foo"};
            spyOn(localStorageService, 'set');
            ctrl.$onDestroy();
            expect(localStorageService.set).toHaveBeenCalledWith('story', ctrl.story);
        });

        it("should decode image to base64 and save them in the local storage", function () {
            ctrl.images = [new Blob(), new Blob()];
            var imagesBase64 = ["data:image/jpeg;base64,/9j/4AA", "data:image/jpeg;base64,/9j/4U22"];

            spyOn(localStorageService, 'set');
            spyOn(ImageBase64Encoder, 'encode').and.returnValue($q.resolve(imagesBase64));
            ctrl.$onDestroy();

            $rootScope.$apply();
            expect(localStorageService.set).toHaveBeenCalledWith('story-images', imagesBase64)
        });

    });


    describe('when we user click in share', function () {
        var createdPost = {data: 'data'};

        beforeEach(function () {
            ctrl.images = [new Blob(), new Blob()];
            ctrl.story = {
                content: 'tonight i will eat in this restaurant, any recommendations plz'
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
        it('should initialize story and images', function () {
            ctrl.story = {data: 'data'};
            ctrl.images = [new Blob(), new Blob()];
            ctrl.shareStory();
            $httpBackend.flush();
            expect(ctrl.story).toEqual({});
            expect(ctrl.images).toEqualData([]);
        });
    });

    describe('images uploader', function () {

        it('should remove selected image', function () {
            var image1 = {name: "image1"};
            var image2 = {name: "image2"};
            ctrl.images = [image1, image2];
            ctrl.deleteImage(image1);
            expect(ctrl.images).toEqual([image2]);
        });
    });

});

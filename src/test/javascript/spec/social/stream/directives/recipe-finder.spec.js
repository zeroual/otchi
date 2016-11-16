describe('FeedLoaderController', function () {

    beforeEach(module('stream', 'fakeModal'));

    var $componentController;
    var $httpBackend;
    beforeEach(inject(function (_$componentController_, _$httpBackend_) {
        $componentController = _$componentController_;
        $httpBackend = _$httpBackend_;
    }));

    describe('on load', function () {

        it('should ask the server to search recipes', function () {
            var bindings = {query: 'tomato'};
            var expectedResult = [{data: 'data'}];
            $httpBackend.expectGET('/rest/v1/recipe/search?query=tomato').respond(200, expectedResult);
            var ctrl = $componentController('recipeFinder', null, bindings);
            $httpBackend.flush();
            expect(ctrl.recipeSearchResults).toEqualData(expectedResult);
        });
    });


});
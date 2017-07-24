describe('Today Recipe component', function () {

    beforeEach(module('otchi'));
    beforeEach(module('directives.templates'));
    beforeEach(mockI18nCalls);

    var ctrl;
    var $httpBackend;

    beforeEach(inject(function ($componentController, _$httpBackend_) {
        var bindings = {};
        $httpBackend = _$httpBackend_;
        ctrl = $componentController('todayRecipe', null, bindings);
    }));

    it('should get the best recipe of the day and show it', function () {
        var bestRecipe = {id:12};

        $httpBackend.expectGET('/api/v1/today-recipe').respond(bestRecipe);
        ctrl.$onInit();
        $httpBackend.flush();
        expect(ctrl.recipe).toEqual(bestRecipe);
    });
});

describe("Timeline", function () {

    beforeEach(module('stream'));
    beforeEach(module('directives.templates'));
    beforeEach(mockI18nCalls);

    var ctrl;
    var feeds = [{data: 'foo'}, {data: 'bar'}];
    var FeedsService;

    beforeEach(inject(function ($componentController, _FeedsService_) {
        FeedsService = _FeedsService_;

        spyOn(FeedsService, 'fetchAllFeeds').and.returnValue(feeds);
        var bindings = {};
        ctrl = $componentController('timeline', null, bindings);
    }));

    it('should load all feeds from the server to show them', function () {
        expect(ctrl.feeds).toEqualData(feeds);
    });

});

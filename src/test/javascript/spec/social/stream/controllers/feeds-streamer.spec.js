describe("tptp", function () {

    beforeEach(module('otchi'));
    beforeEach(module('directives.templates'));
    beforeEach(mockI18nCalls);

    var ctrl;
    var feeds = [{data: 'foo'}, {data: 'bar'}];
    var FeedsService;
    var $q;
    var $rootScope;

    beforeEach(inject(function ($componentController, _FeedsService_, _$q_, _$rootScope_) {
        FeedsService = _FeedsService_;
        $q = _$q_;
        $rootScope = _$rootScope_;

        spyOn(FeedsService, 'fetchAllFeeds').and.returnValue(feeds);
        ctrl = $componentController('feedsStreamer', null, {});
    }));

    describe('on initialization', function () {
        it('should load all feeds from the server to show them', function () {
            expect(ctrl.feeds).toEqualData(feeds);
        });
    });

    describe('Remove feed from the stream', function () {

        it('should remove the feed from the stream', function () {
            spyOn(FeedsService, 'removeFeed').and.returnValue($q.resolve(201));
            ctrl.feeds = [{id: 1}, {id: 2}];
            ctrl.removeFeed({id: 1});
            $rootScope.$apply();
            expect(ctrl.feeds).toEqualData([{id: 2}]);
        });

        it('should not remove any feed if the server return an error', function () {
            spyOn(FeedsService, 'removeFeed').and.returnValue($q.reject());
            ctrl.feeds = [{id: 1}, {id: 2}];
            ctrl.removeFeed({id: 1});
            $rootScope.$apply();
            expect(ctrl.feeds).toEqualData([{id: 1}, {id: 2}]);
        });
    });
});

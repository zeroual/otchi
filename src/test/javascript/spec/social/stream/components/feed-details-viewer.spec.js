describe('FeedViewer Directive', function () {

    beforeEach(module('stream'));
    beforeEach(module('directives.templates'));

    var ctrl;
    var feed;
    var AnalyticsService;

    beforeEach(inject(function ($componentController, _AnalyticsService_) {
        AnalyticsService = _AnalyticsService_;
        feed = {id: 1, likes: []};
        var bindings = {feed: feed};
        spyOn(AnalyticsService, 'incrementFeedViews');
        ctrl = $componentController('feedDetailsViewer', null, bindings);

    }));

    it('should call the analytics service to increment feed views', function () {
        expect(AnalyticsService.incrementFeedViews).toHaveBeenCalledWith(feed);
    });
});

describe('Feed comments viewer Directive', function () {

    beforeEach(module('stream'));
    beforeEach(module('directives.templates'));

    var ctrl;
    var FeedsService;
    var rootScope;
    var $q;
    beforeEach(inject(function (_FeedsService_, $componentController, $rootScope,_$q_) {
        FeedsService = _FeedsService_;
        ctrl = $componentController('feedComments', null, {});
        rootScope = $rootScope;
        $q = _$q_;
    }));

    describe('comment on feed', function () {

        var feed;

        var comment = "comment content";
        beforeEach(function () {
            feed = {id: 1, comments: []};
            ctrl.feed = {id: 1, comments: []};
            ctrl.commentContent = comment;
        });

        it('should call the FeedsService to comment on feed', function () {
            spyOn(FeedsService, 'commentOnFeed').and.callFake($q.resolve);
            ctrl.commentOnFeed();
            rootScope.$apply();
            expect(FeedsService.commentOnFeed).toHaveBeenCalledWith(feed, comment);
        });

        xit('should reset the comment content input after commenting', function () {
            spyOn(FeedsService, 'commentOnFeed').and.callFake($q.resolve);
            ctrl.commentOnFeed();
            rootScope.$apply();
            expect(ctrl.commentContent).toEqual('');
        });
    });

});
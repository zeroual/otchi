describe('FeedViewer Directive', function () {

    beforeEach(module('stream','fakeModal'));
    beforeEach(module('directives.templates'));

    var ctrl;
    var onLikeSpy;
    var onUnLikeSpy;
    var onRemoveSpy;
    var FeedsService;
    var $uibModal;
    var feed;
    beforeEach(inject(function ($componentController, _$uibModal_,_FeedsService_) {
        $uibModal = _$uibModal_;
        FeedsService = _FeedsService_;

        feed = {id: 1, likes: []};

        onLikeSpy = jasmine.createSpy('onLike');
        onUnLikeSpy = jasmine.createSpy('onUnLike');
        onRemoveSpy = jasmine.createSpy('onRemove');

        var bindings = {feed: feed, onLike: onLikeSpy, onUnLike: onUnLikeSpy, onRemove: onRemoveSpy};
        ctrl = $componentController('feedViewer', null, bindings);

    }));

    describe("delegate actions on feed to the parent component", function () {

        it('should call the FeedsService to perform like', function () {
            spyOn(FeedsService, 'likeFeed');
            ctrl.like();
            expect(FeedsService.likeFeed).toHaveBeenCalledWith(feed);
        });

        it('should call the FeedsService to perform like', function () {
            spyOn(FeedsService, 'unLikeFeed');
            ctrl.unLike();
            expect(FeedsService.unLikeFeed).toHaveBeenCalledWith(feed);
        });

        it('should open modal de confirm feed suppression', function () {
            spyOn($uibModal, 'open');
            ctrl.remove();
            expect($uibModal.open).toHaveBeenCalled();
        });
    });
});

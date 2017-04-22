describe('Feeds Service', function () {

    beforeEach(module('stream'));

    var $httpBackend;
    var FeedsService;
    var Principal;
    beforeEach(inject(function (_Principal_, _FeedsService_, _$httpBackend_) {
        FeedsService = _FeedsService_;
        $httpBackend = _$httpBackend_;
        Principal = _Principal_;
    }));


    describe('Like Feed', function () {

        var feeds;
        beforeEach(function () {
            feeds = [{id: 1, likes: [], likesCount: 0}];
        });

        it('should ask the server to perform post like', function () {
            $httpBackend.expectPOST('/rest/v1/feed/1/like').respond(200);
            FeedsService.likeFeed(feeds[0]);
            $httpBackend.flush();
        });

        it('should marque that post as liked', function () {
            $httpBackend.expectPOST('/rest/v1/feed/1/like').respond(200);
            FeedsService.likeFeed(feeds[0]);
            $httpBackend.flush();
            expect(feeds[0].liked).toBeTruthy();
            expect(feeds[0].likesCount).toBe(1);
        });

        it('should not marque that post as liked if server return error', function () {
            $httpBackend.expectPOST('/rest/v1/feed/1/like').respond(409);
            FeedsService.likeFeed(feeds[0]);
            $httpBackend.flush();
            expect(feeds[0].liked).toBeFalsy();
            expect(feeds[0].likesCount).toBe(0);
        });
    });

    describe('unLike Feed', function () {

        var feeds;
        beforeEach(function () {
            feeds = [{id: 1, liked: true, likesCount: 1}];
        });

        it('should ask the server to perform post unlike', function () {
            $httpBackend.expectPOST('/rest/v1/feed/1/unlike').respond(200);
            FeedsService.unLikeFeed(feeds[0]);
            $httpBackend.flush();
        });

        it('should marque that post as unliked', function () {
            $httpBackend.expectPOST('/rest/v1/feed/1/unlike').respond(200);
            FeedsService.unLikeFeed(feeds[0]);
            $httpBackend.flush();
            expect(feeds[0].liked).toBeFalsy();
            expect(feeds[0].likesCount).toBe(0);
        });

        it('', function () {
            $httpBackend.expectPOST('/rest/v1/feed/1/unlike').respond(508);
            FeedsService.unLikeFeed(feeds[0]);
            $httpBackend.flush();
            expect(feeds[0].liked).toBeTruthy();
            expect(feeds[0].likesCount).toBe(1);
        });
    });
});

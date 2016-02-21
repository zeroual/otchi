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

    describe('Fetch Feeds', function () {
        var currentAccount = {id: 2};
        var posts = [{id: 1, likes: [{id: 1}]}, {id: 2, likes: [{id: 1}, {id: 2}]}];

        it('should fetch feeds form the back end and mark liked feeds by the current user', function () {
            spyOn(Principal, 'identity').and.callFake(function () {
                return {
                    then: function (cb) {
                        return cb(currentAccount);
                    }
                };
            });
            $httpBackend.expectGET('/rest/v1/feed').respond(posts);
            var feeds = FeedsService.fetchAllFeeds();
            $httpBackend.flush();
            expect(feeds[0].liked).toBeFalsy();
            expect(feeds[1].liked).toBeTruthy();
        });
    });

    describe('Like Feed', function () {
        var currentAccount = {id: 2};
        var posts = [{id: 1, likes: []}];

        beforeEach(function () {
            spyOn(Principal, 'identity').and.callFake(function () {
                return {
                    then: function (cb) {
                        return cb(currentAccount);
                    }
                };
            });
        });

        it('should fetch feeds form the back end and mark liked feeds by the current user', function () {
            $httpBackend.expectGET('/rest/v1/feed').respond(posts);
            var feeds = FeedsService.fetchAllFeeds();
            $httpBackend.flush();
            expect(feeds[0].liked).toBeFalsy();
        });

        it('should ask the server to perform post like', function () {
            $httpBackend.expectPOST('/rest/v1/feed/1/like').respond(200);
            FeedsService.likePost(posts[0]);
            $httpBackend.flush();
        });

        it('should marque that post as liked', function () {
            FeedsService.likePost(posts[0]);
            expect(posts[0].liked).toBeTruthy();
        });

        it('should add current user to likes list', function () {
            FeedsService.likePost(posts[0]);
            expect(posts[0].likes).toContain(currentAccount);
        });
    });

    describe('unLike Feed', function () {
        var currentAccount = {id: 2};
        var posts = [{id: 1, likes: []}];

        beforeEach(function () {
            spyOn(Principal, 'identity').and.callFake(function () {
                return {
                    then: function (cb) {
                        return cb(currentAccount);
                    }
                };
            });
        });

        it('should ask the server to perform post unlike', function () {
            $httpBackend.expectPOST('/rest/v1/feed/1/unlike').respond(200);
            FeedsService.unLikePost(posts[0]);
            $httpBackend.flush();
        });

        it('should marque that post as not liked', function () {
            FeedsService.likePost(posts[0]);
            expect(posts[0].liked).toBeTruthy();
            FeedsService.unLikePost(posts[0]);
            expect(posts[0].liked).toBeFalsy();
        });

        it('should remove current user from likes list', function () {
            FeedsService.likePost(posts[0]);
            FeedsService.unLikePost(posts[0]);
            expect(posts[0].likes).not.toContain(currentAccount);
        });
    });
});
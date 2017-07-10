describe('Chef Service', function(){
    beforeEach(module('profile'));

    var $httpBackend;
    var ChefService;

    beforeEach(inject(function( _ChefService_, _$httpBackend_){
        $httpBackend = _$httpBackend_;
        ChefService = _ChefService_;
    }));

    describe('Chef feeds', function(){
        it('Should ask the server to get chef feeds', function(){
            $httpBackend.expectGET('/rest/v1/chef/1/feeds').respond(200);
            ChefService.fetchChefFeeds(1);
            $httpBackend.flush();
        });
    });

});
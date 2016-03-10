describe('FollowingController', function () {

    beforeEach(module('stream'));

    var $scope;
    var $rootScope;
    var $httpBackend;
    var followingRecommendation = [
        {id: 1, firstName: 'Amine', lastName: 'jabri', email: 'm.amine.jabri@gmail.com'},
        {id: 2, firstName: 'Abdel', lastName: 'Zerwal', email: 'abdel@gmail.com'}
    ];

    var followingUser = {id: 2, firstName: 'Abdel', lastName: 'Zerwal', email: 'abdel@gmail.com'}

    beforeEach(inject(function ($controller, _$rootScope_, _$httpBackend_) {
        $rootScope = _$rootScope_;
        $scope = $rootScope.$new();
        $httpBackend = _$httpBackend_;
        $httpBackend.expectGET('/rest/v1/recommendations/followings').respond(followingRecommendation);
        $controller('FollowingsRecommendationController', {$scope: $scope});
        $httpBackend.flush();
    }));

    describe('On Load', function () {
        it('should load all following suggestion from the server to show them', function () {
            expect($scope.followingRecommendations).toEqualData(followingRecommendation);
        });
    });

    describe('follow user', function () {

        beforeEach(function () {
            $httpBackend.expectPOST('/rest/v1/me/following', 2).respond(200);
        });

        it('should ask the back end to follow user', function () {
            $scope.followUser(followingUser);
            $httpBackend.flush();
        });

        it('should delete user from list of recommendations following user', function () {
            $scope.followUser(followingUser);
            $httpBackend.flush();
            expect($scope.followingRecommendations.length).toEqual(1);
            expect($scope.followingRecommendations[0].firstName).toEqual('Amine');
        })
    })

});
describe('NavBarController Tests', function () {

    beforeEach(module('otchi'));
    beforeEach(module('directives.templates'));

    var $scope, $controller;
    var $httpBackend;
    var expectedNotifications;
    var expectAuthenticatedUser;

    beforeEach(inject(function (_$rootScope_, _$httpBackend_, _$controller_) {
        $httpBackend = _$httpBackend_;
        $controller = _$controller_;
        $rootScope = _$rootScope_;
    }));

    beforeEach(function () {
        expectedNotifications = [{data: 'foo'}];
        expectAuthenticatedUser = {foo: 'toto'};
        $httpBackend.expectGET('/rest/v1/me/notifications?unread=true').respond(expectedNotifications);
        $httpBackend.expectGET('/rest/v1/me').respond(200, expectAuthenticatedUser);
    });

    function instantiateController() {
        $scope = $rootScope.$new();
        $controller('NavBarController', {$scope: $scope});
        return $scope;
    }
    
    describe('User personal information', function () {

        it('should get authenticated user information to show them', function () {
            $scope = instantiateController();
            $httpBackend.flush();
            expect($scope.account).toEqualData(expectAuthenticatedUser);
        });
    });

    describe('Notifications', function () {

        it('should load unread notifications for authenticated user', function () {
            $scope = instantiateController();
            $httpBackend.flush();
            expect($scope.notifications).toEqualData(expectedNotifications);

        });

    });
});
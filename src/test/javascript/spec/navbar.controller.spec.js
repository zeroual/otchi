describe('NavBarController Tests', function () {

    beforeEach(module('otchi'));
    beforeEach(module('directives.templates'));

    var $scope, $controller;
    var $httpBackend;
    var expectedNotifications;
    var expectAuthenticatedUser;
    var $state;

    beforeEach(inject(function (_$rootScope_, _$httpBackend_, _$controller_, _$state_) {
        $httpBackend = _$httpBackend_;
        $controller = _$controller_;
        $rootScope = _$rootScope_;
        $state = _$state_
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

        beforeEach(function () {
            $scope = instantiateController();
            $httpBackend.flush();
        });

        it('should load unread notifications for authenticated user', function () {
            expect($scope.notifications).toEqualData(expectedNotifications);

        });

        describe('mark as read', function () {
            beforeEach(function () {
                $httpBackend.expectPUT('/rest/v1/me/notifications/3', {unread: false}).respond(200);
            });

            it('should ask the server to mark the notification as read', function () {
                $httpBackend.expectGET('/rest/v1/feed/3').respond(200);
                var notification = {id: 3, postId: 3};
                $scope.readNotification(notification);
                $httpBackend.flush();
            });

            it('should mark local notification as read', function () {
                $httpBackend.expectGET('/rest/v1/feed/3').respond(200);
                var notification = {id: 3, unread: true, postId: 3};
                $scope.readNotification(notification);
                $httpBackend.flush();
                expect(notification.unread).toBeFalsy();
            });

            it('should redirect user to a specific page to display post', function () {
                spyOn($state, 'go');
                var notification = {id: 3, unread: true, postId: 3};
                $scope.readNotification(notification);
                $httpBackend.flush();
                expect($state.go).toHaveBeenCalledWith("showPost", {postId: 3});
            });
        });

    });

    describe('Recipes search', function () {

        beforeEach(function () {
            $scope = instantiateController();
        });

        describe('Recipes suggestions', function () {

            it('should ask the server to suggest recipes based on what user input the form', function () {
                $scope.recipeQuery = 'tomato';
                var expectedRecipeSuggestions = [
                    {title: 'Double Tomato Bruschetta'}
                ];
                $httpBackend.expectGET('/rest/v1/recipe/search/suggest?query=' + $scope.recipeQuery).respond(200, expectedRecipeSuggestions);
                $scope.suggestRecipes();
                $httpBackend.flush();
                expect($scope.recipeSuggestions).toEqualData(expectedRecipeSuggestions);
            });
        });

        describe('Recipes search', function () {

            it('should redirect to search page when user click on go', function () {
                $scope.recipeQuery = 'tomato';
                spyOn($state, 'go');
                $scope.searchRecipes();
                expect($state.go).toHaveBeenCalledWith('search', {query: $scope.recipeQuery});
            });
        });
    });


});

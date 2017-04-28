describe('NavBarController Tests', function () {

    beforeEach(module('otchi'));
    beforeEach(module('directives.templates'));
    beforeEach(mockI18nCalls);


    var ctrl;
    var NotificationsService, Auth;
    var expectAuthenticatedUser = {data: 'data'};
    var expectedNotifications = [{id: 1}, {id: 2}];
    var $state, $rootScope;

    beforeEach(inject(function (_$state_, $componentController, $q, Principal, _$rootScope_, _NotificationsService_, _Auth_) {
            $state = _$state_;
            NotificationsService = _NotificationsService_;
            Auth = _Auth_;
            $rootScope = _$rootScope_;

            spyOn(Principal, 'identity').and.returnValue($q.resolve(expectAuthenticatedUser));
            spyOn(NotificationsService, 'getUnreadNotifications').and.returnValue($q.resolve(expectedNotifications));
            spyOn(NotificationsService, 'markNotificationAsRead').and.returnValue($q.resolve());
            spyOn($state, 'go');
            spyOn(Auth, 'logout');

            ctrl = $componentController('navbarAuthenticated', null, {});
            $rootScope.$apply();
        })
    )
    ;


    describe('User personal information', function () {

        it('should get authenticated user information to show them', function () {
            expect(ctrl.account).toEqualData(expectAuthenticatedUser);
        });
    });

    describe('Notifications', function () {

        it('should load unread notifications for authenticated user', function () {
            expect(ctrl.notifications).toEqualData(expectedNotifications);

        });

        describe('mark as read', function () {

            it('should change notification state to read', function () {

                var notification = {id: 3, postId: 3, unread: true};
                ctrl.readNotification(notification);
                expect(NotificationsService.markNotificationAsRead).toHaveBeenCalledWith(3);
                $rootScope.$apply();
                expect(notification.unread).toBeFalsy();
            });

            it('should redirect user to a specific page to display post ', function () {
                var notification = {id: 3, unread: true, postId: 3};
                ctrl.readNotification(notification);
                $rootScope.$apply();
                expect($state.go).toHaveBeenCalledWith("showPost", {postId: 3});
            });
        });

    });

    describe('Logout', function () {

        it('should logout user', function () {
            ctrl.logout();
            expect(Auth.logout).toHaveBeenCalled();
        });

        it('should redirect user to index page after logout', function () {
            ctrl.logout();
            $rootScope.$apply();
            expect($state.go).toHaveBeenCalledWith('index');
        });
    });
});
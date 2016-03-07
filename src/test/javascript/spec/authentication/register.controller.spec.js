describe('Controller Tests', function () {

    beforeEach(module('authentication'));

    describe('RegisterController', function () {

        var $scope, $q; // actual implementations
        var MockTranslate, MockAccountService, MockAuth, MockState; // mocks

        beforeEach(inject(function (_$q_, $rootScope, $controller) {
            $q = _$q_;
            $scope = $rootScope.$new();
            MockAccountService = jasmine.createSpyObj('MockAccountService', ['register']);
            MockTranslate = jasmine.createSpyObj('MockTranslate', ['use']);
            MockAuth = jasmine.createSpyObj('Auth', ['login']);
            MockState = jasmine.createSpyObj('$state', ['go']);

            var locals = {
                '$scope': $scope,
                'AccountService': MockAccountService,
                'Auth': MockAuth,
                '$translate': MockTranslate,
                '$state': MockState
            };
            $controller('RegisterController', locals);
        }));

        it('should register the new user and automatically authenticate him',
            function () {
                // given
                MockTranslate.use.and.returnValue('en');
                MockAccountService.register.and.returnValue($q.resolve());
                MockAuth.login.and.returnValue($q.resolve());
                MockState.go.and.returnValue($q.resolve());

                $scope.account.password = 'password';
                $scope.account.email = 'email';
                // when
                $scope.$apply($scope.register); // $q promises require an $apply
                // then
                expect(MockAccountService.register).toHaveBeenCalledWith({
                    password: 'password',
                    email: 'email',
                    langKey: 'en'
                });
                expect(MockTranslate.use).toHaveBeenCalled();
                expect($scope.errorEmailExists).toBeNull();
                expect($scope.error).toBeNull();
                expect(MockAuth.login).toHaveBeenCalledWith({
                    email: 'email',
                    password: 'password',
                    rememberMe: false
                });
                expect(MockState.go).toHaveBeenCalledWith('feed');
            });

        it('should notify of email existence upon 409', function () {
            // given
            MockAccountService.register.and.returnValue($q.reject({
                status: 409
            }));
            // when
            $scope.$apply($scope.register); // $q promises require an $apply
            // then
            expect($scope.errorEmailExists).toEqual('ERROR');
            expect($scope.error).toBeNull();
        });

        it('should notify of generic error', function () {
            // given
            MockAccountService.register.and.returnValue($q.reject({
                status: 503
            }));
            // when
            $scope.$apply($scope.register); // $q promises require an $apply
            // then
            expect($scope.errorEmailExists).toBeNull();
            expect($scope.error).toEqual('ERROR');
        });

        it('should redirect to login page if automatically login fail after success registration', function () {
            // given
            MockAccountService.register.and.returnValue($q.resolve());
            MockAuth.login.and.returnValue($q.reject(500));
            MockState.go.and.returnValue($q.resolve());
            // when
            $scope.$apply($scope.register); // $q promises require an $apply
            //then
            expect(MockState.go).toHaveBeenCalledWith('login');
        });

    });
});
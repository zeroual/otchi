describe('Main navbar', function () {


    beforeEach(module('otchi'));
    beforeEach(module('directives.templates'));
    beforeEach(mockI18nCalls);

    var ctrl;
    var Principal;
    var $componentController;
    var $rootScope;

    beforeEach(inject(function (_$componentController_, _Principal_,$q,_$rootScope_) {
        $rootScope = _$rootScope_;

        Principal = _Principal_;
        spyOn(Principal, 'identity').and.returnValue($q.resolve());
        $componentController = _$componentController_;
    }));

    it('should indicate that user is not authenticated', function () {

        spyOn(Principal, 'isAuthenticated').and.returnValue(false);
        ctrl = $componentController('navbar', null, {});
        $rootScope.$apply();
        expect(ctrl.isAuthenticated).toBeFalsy();
    });

    it('should indicate that user is  authenticated', function () {
        spyOn(Principal, 'isAuthenticated').and.returnValue(true);
        ctrl = $componentController('navbar', null, {});
        $rootScope.$apply();
        expect(ctrl.isAuthenticated).toBe(true);
    });
});
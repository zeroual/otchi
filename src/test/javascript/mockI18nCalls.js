function mockI18nCalls() {
    inject(function ($httpBackend) {
        $httpBackend.whenGET(/i18n\/.*\/.+\.json/).respond({});
    });
}
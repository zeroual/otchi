function createMatcher(fn) {
    return {
        compare: function (actual, expected) {
            return {
                pass: fn(actual, expected)
            };
        }
    }
}

beforeEach(function () {
    jasmine.addMatchers({
        toEqualData: function () {
            return createMatcher(angular.equals);
        }
    });
});
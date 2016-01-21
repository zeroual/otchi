describe('timeAgo Filter ', function () {

    beforeEach(module('helpers'));

    var timeAgoFilter;
    beforeEach(inject(function (_timeAgoFilter_) {
        moment.locale('en');
        timeAgoFilter = _timeAgoFilter_;

    }));

    beforeEach(function () {
        jasmine.clock().install();
    });

    afterEach(function () {
        jasmine.clock().uninstall();
    });

    it('should map a date to time a go', function () {
        var baseTime = moment("2016-02-28 00:00:00").toDate();
        jasmine.clock().mockDate(baseTime);
        var date = '2012-02-02';
        expect(timeAgoFilter(date)).toEqual('4 years ago');

        var date = '2016-01-02';
        expect(timeAgoFilter(date)).toEqual('2 months ago');

        var date = '2016-02-22';
        expect(timeAgoFilter(date)).toEqual('6 days ago');

        var date = '2016-02-28 14:20:00';
        expect(timeAgoFilter(date)).toEqual('in 14 hours');

        var date = '2016-02-28 00:20:00';
        expect(timeAgoFilter(date)).toEqual('in 20 minutes');
    });

});
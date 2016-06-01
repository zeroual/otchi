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
        var baseTime = moment("2016-03-28 00:00:00").toDate();
        jasmine.clock().mockDate(baseTime);
        var date = moment('2012-03-02').toDate();
        expect(timeAgoFilter(date)).toEqual('4 years ago');

        var date = moment('2016-01-01').toDate();
        expect(timeAgoFilter(date)).toEqual('3 months ago');

        var date = '2016-03-22';
        expect(timeAgoFilter(date)).toEqual('6 days ago');

        var date = moment('2016-03-28 14:20:00').toDate();
        expect(timeAgoFilter(date)).toEqual('in 14 hours');

        var date = moment('2016-03-28 00:02:00').toDate();
        expect(timeAgoFilter(date)).toEqual('in 2 minutes');

        var date = moment('2016-03-28 00:00:08').toDate();
        expect(timeAgoFilter(date)).toEqual('in a few seconds');
    });

});

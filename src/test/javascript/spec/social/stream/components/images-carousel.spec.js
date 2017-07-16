describe('FeedViewer Directive', function () {

    beforeEach(module('stream'));
    beforeEach(module('directives.templates'));

    var ctrl;
    var images;
    beforeEach(inject(function ($componentController) {

        images = ['image1', 'image2'];

        var bindings = {images: images};
        ctrl = $componentController('imagesCarousel', null, bindings);
        ctrl.$onInit();
    }));

    it('should display images in slides', function () {
        var expectedSlides = [{image: 'image1',id:0}, {image: 'image2',id:1}];
        expect(ctrl.slides).toEqualData(expectedSlides  )
    });

    it('should init the slid interval to 3s', function () {
        expect(ctrl.interval).toEqual(3000);
    });
});

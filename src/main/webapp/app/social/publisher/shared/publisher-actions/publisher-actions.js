angular.module("publisher")
    .component('publisherActions', {
        templateUrl: 'app/social/publisher/shared/publisher-actions/publisher-actions.template.html',
        controller: function () {

            $('.fab').hover(function () {
                $(this).toggleClass('active');
            });

            $(function () {
                $('[data-toggle="tooltip"]').tooltip()
            });
        }
    });
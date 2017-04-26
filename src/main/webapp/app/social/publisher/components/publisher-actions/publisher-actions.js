angular.module("publisher")
    .component('publisherActions', {
        templateUrl: 'app/social/publisher/components/publisher-actions/publisher-actions.html',
        controller: function () {

            $('.fab').hover(function () {
                $(this).toggleClass('active');
            });

            $(function () {
                $('[data-toggle="tooltip"]').tooltip()
            });
        }
    });

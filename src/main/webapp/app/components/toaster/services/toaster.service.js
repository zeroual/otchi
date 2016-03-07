'use strict';

//FIXME USE $Translate to display messages

angular.module('toaster')
    .service('Toaster', function (ngToast) {
        var self = this;

        self.success = function open(messageKey, messageParams, options) {
            ngToast.create(angular.extend({
                content: messageKey,
                className: 'success',
                dismissButton: true,
                dismissOnTimeout: false
            }, options));
        };

        self.error = function open(messageKey, messageParams, options) {
            ngToast.create(angular.extend({
                content: messageKey,
                className: 'danger',
                dismissButton: true,
                dismissOnTimeout: false
            }, options));
        };
    });

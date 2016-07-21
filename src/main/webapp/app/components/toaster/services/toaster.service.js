'use strict';

angular.module('toaster')
    .service('ToasterService', function (ngToast) {
        var self = this;

        self.success = function open(messageKey, messageParams, options) {
            ngToast.create(angular.extend({
                content: messageKey,
                className: 'success',
                dismissButton: true,
                dismissOnTimeout: true
            }, options));
        };

        self.error = function open(messageKey, messageParams, options) {
            ngToast.create(angular.extend({
                content: messageKey,
                className: 'danger',
                dismissButton: true,
                dismissOnTimeout: true
            }, options));
        };
    });

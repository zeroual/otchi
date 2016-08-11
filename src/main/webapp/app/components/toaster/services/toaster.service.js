'use strict';

angular.module('toaster')
    .service('ToasterService', function (ngToast) {
        var self = this;

        function showNotification(messageKey, options, className) {
            ngToast.create(angular.extend({
                content: messageKey,
                className: className,
                dismissButton: true,
                dismissOnTimeout: true
            }, options));
        }

        self.success = function open(messageKey, messageParams, options) {
            showNotification(messageKey, options, 'success');
        };

        self.error = function open(messageKey, messageParams, options) {
            showNotification(messageKey, options, 'danger');
        };

        self.info = function open(messageKey, messageParams, options) {
            showNotification(messageKey, options, 'info');
        };

    });
